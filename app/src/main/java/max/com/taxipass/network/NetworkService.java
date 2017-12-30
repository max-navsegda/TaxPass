package max.com.taxipass.network;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import max.com.taxipass.activity.LoginActivity;
import max.com.taxipass.events.ConnectionErrorEvent;
import max.com.taxipass.events.ErrorMessageEvent;
import max.com.taxipass.events.MoveNextEvent;
import max.com.taxipass.events.OrderEvent;
import max.com.taxipass.events.SimChangedEvent;
import max.com.taxipass.events.TypePhoneEvent;
import max.com.taxipass.events.UpdateAdapterEvent;
import max.com.taxipass.model.OrderDto;
import max.com.taxipass.model.UserProfileDto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static max.com.taxipass.activity.LoginActivity.APP_PREFERENCES_SIMID;
import static max.com.taxipass.activity.LoginActivity.prevGEmail;


public class NetworkService {
    private RetrofitConfig retrofitConfig;

    public NetworkService() {
        retrofitConfig = new RetrofitConfig();
    }

    public void register(final String login, final String password) {
        Call<Boolean> call = retrofitConfig.getApiNetwork().register(login, password);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.body()) {
                    EventBus.getDefault().post(new TypePhoneEvent());
                }
                else {
                    if (!LoginActivity.settings.getString(APP_PREFERENCES_SIMID, "").equals(LoginActivity.m.getSimSerialNumber())) {
                        if(LoginActivity.count  ==1){
                            prevGEmail = login;}
                        if(LoginActivity.count == 0){
                            login(login, password);
                        }
                        EventBus.getDefault().post(new SimChangedEvent());
                    } else {
                        login(login, password);
                    }
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                EventBus.getDefault().post(new ConnectionErrorEvent(true));
            }
        });
    }

    public void setDataToProfile(String email, String firstName, String lastName, String phone) {
        Call<UserProfileDto> call = retrofitConfig.getApiNetwork().setDataToProfile(email, firstName, lastName, phone);
        call.enqueue(new Callback<UserProfileDto>() {
            @Override
            public void onResponse(Call<UserProfileDto> call, Response<UserProfileDto> response) {
                try {
                    UserProfileDto.User.setPhone(response.body().getPhone());
                    UserProfileDto.User.setFirstName(response.body().getFirstName());
                    UserProfileDto.User.setLastName(response.body().getLastName());
                    UserProfileDto.User.setEmail(response.body().getEmail());
                    EventBus.getDefault().post(new MoveNextEvent());
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorMessageEvent("Неизвестная ошибка"));
                }
            }

            @Override
            public void onFailure(Call<UserProfileDto> call, Throwable t) {
                EventBus.getDefault().post(new ErrorMessageEvent("Неизвестная ошибка"));
            }
        });
    }

    private void login(final String login, final String password) {
        Call<UserProfileDto> call = retrofitConfig.getApiNetwork().login(login, password);
        call.enqueue(new Callback<UserProfileDto>() {
            @Override
            public void onResponse(Call<UserProfileDto> call, Response<UserProfileDto> response) {
                try {
                    UserProfileDto.User.setPhone(response.body().getPhone());
                    UserProfileDto.User.setFirstName(response.body().getFirstName());
                    UserProfileDto.User.setLastName(response.body().getLastName());
                    UserProfileDto.User.setEmail(response.body().getEmail());
                    EventBus.getDefault().post(new MoveNextEvent());
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorMessageEvent("Your phone used"));
                }
            }

            @Override
            public void onFailure(Call<UserProfileDto> call, Throwable t) {
                EventBus.getDefault().post(new ConnectionErrorEvent(true));
            }
        });
    }

    public void makeOrder(String pointA, String pointB, Date date,
                          double[] pointACoordinate, double[] pointBCoordinate) {
        Call<OrderDto> call = retrofitConfig.getApiNetwork().makeOrder(pointA, pointB, date.getTime(),
                UserProfileDto.User.getPhone(), "new", pointACoordinate, pointBCoordinate);
        call.enqueue(new Callback<OrderDto>() {
            @Override
            public void onResponse(Call<OrderDto> call, Response<OrderDto> response) {
                try {
                    OrderDto.Oreders.add(response.body());
                    EventBus.getDefault().post(new OrderEvent());
                    EventBus.getDefault().post(new ErrorMessageEvent("Когда водитель отвезёт, нажмите " + "\"Завершить\""));
                } catch (Exception e) {
                    EventBus.getDefault().post(new ErrorMessageEvent("Вы еще не завершили предыдущий заказ"));
                }
            }

            @Override
            public void onFailure(Call<OrderDto> call, Throwable t) {
                EventBus.getDefault().post(new ConnectionErrorEvent(true));
            }
        });
    }

    public void getOrders(String userPhone) {
        Call<List<OrderDto>> call = retrofitConfig.getApiNetwork().getOrders(userPhone);
        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                OrderDto.Oreders.setItems(response.body());
                EventBus.getDefault().post(new ConnectionErrorEvent(false));
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable t) {
                EventBus.getDefault().post(new ConnectionErrorEvent(true));
            }
        });
    }

    public void removeOrder(final OrderDto orderDto) {
        Call<Boolean> call = retrofitConfig.getApiNetwork().removeOrder(orderDto.getId());
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                OrderDto.Oreders.getOrders().remove(orderDto);
                EventBus.getDefault().post(new UpdateAdapterEvent());
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                EventBus.getDefault().post(new ConnectionErrorEvent(true));
            }
        });
    }

    public void setCoordinate(Double lat, Double lng) {
        Call<Void> call = retrofitConfig.getApiNetwork().setCoordinate(UserProfileDto.User.getPhone(), lat, lng);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                EventBus.getDefault().post(new ConnectionErrorEvent(false));
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                EventBus.getDefault().post(new ConnectionErrorEvent(true));
            }
        });
    }
}
