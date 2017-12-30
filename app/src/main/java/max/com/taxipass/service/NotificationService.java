package max.com.taxipass.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import max.com.taxipass.R;
import max.com.taxipass.activity.MapsActivity;
import max.com.taxipass.events.OrderAccepted;



public class NotificationService extends Service {
    Notification notification;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        if(EventBus.getDefault().hasSubscriberForEvent(OrderAccepted.class)){
            Log.e("TAG", "SUBSCRIBED");
        };
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe
    public void OnOrderAccepted(OrderAccepted event){
        if(MapsActivity.orderDto !=null) {
            if (MapsActivity.orderDto.getStatus().equals("accepted")) {
                Log.e("NOTIFICATION", "NOTIFICATION");

                notification = new android.app.Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Статус вашего заказа")
                        .setContentText("Принят")
                        .build();
            } else if (MapsActivity.orderDto.getStatus().equals("accepted")) {
                Log.e("NOTIFICATION", "NOTIFICATION");
                notification = new android.app.Notification.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Статус вашего заказа")
                        .setContentText("Еще не принят")
                        .build();
            }
            startForeground(108, notification);
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        try {
            EventBus.getDefault().unregister(this);
        }catch (Exception e){

        }
        super.onDestroy();
    }
}

