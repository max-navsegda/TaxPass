package max.com.taxipass.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import max.com.taxipass.R;
import max.com.taxipass.events.UpdateAdapterEvent;
import max.com.taxipass.model.OrderDto;
import max.com.taxipass.network.NetworkService;


public class OrderAdapter extends BaseAdapter {
    private Context context;

    public OrderAdapter(Context context) {
        this.context = context;
    }
    java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
    @Override
    public int getCount() {
        return OrderDto.Oreders.getOrders().size();
    }

    @Override
    public Object getItem(int position) {
        return OrderDto.Oreders.getOrders().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrderDto orderDto = OrderDto.Oreders.getOrders().get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.order_list_style, parent, false);


        try {
            ((TextView) convertView.findViewById(R.id.addressTV)).setText(orderDto.getPoints());
            ((TextView) convertView.findViewById(R.id.whenTV)).setText(simpleDateFormat.format(orderDto.getTime()));
            final Button b = (Button) convertView.findViewById(R.id.close);
            final View finalConvertView = convertView;


           if(orderDto.getStatus().equals("accepted")){
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(finalConvertView.getContext(), "Завершить заказ нельзя пока он принят водителем", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
               b.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Log.e("BUTTON", "BUTTON");
                       new NetworkService().removeOrder(orderDto);
                       EventBus.getDefault().post(new UpdateAdapterEvent());
                   }
               });
           }
        }
        catch (Exception e) {
            Log.e("TAG", e.toString());
            convertView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
