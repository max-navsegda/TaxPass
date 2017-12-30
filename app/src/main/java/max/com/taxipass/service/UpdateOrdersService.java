package max.com.taxipass.service;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import max.com.taxipass.activity.MapsActivity;
import max.com.taxipass.events.OrderAccepted;
import max.com.taxipass.events.UpdateAdapterEvent;
import max.com.taxipass.model.UserProfileDto;
import max.com.taxipass.network.NetworkService;



public class UpdateOrdersService extends IntentService {
    public static boolean isRun = true;

    public UpdateOrdersService() {
        super("OrdersService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    new NetworkService().getOrders(UserProfileDto.User.getPhone());
                    EventBus.getDefault().post(new UpdateAdapterEvent());
                    if(MapsActivity.orderDto!=null){
                    if(MapsActivity.orderDto.getStatus().equals("accepted")){
                        EventBus.getDefault().post(new OrderAccepted());
                    }}
                    try {
                        TimeUnit.SECONDS.sleep(8);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}