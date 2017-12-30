package max.com.taxipass.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import max.com.taxipass.R;
import max.com.taxipass.model.OrderDto;


public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("HH:mm");
    public MarkerAdapter(Context context) {
        this.context = context;
    }
    View convertView;
    OrderDto orderDto;
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        try {
            orderDto = OrderDto.Oreders.getOrders().get(OrderDto.Oreders.getOrders().size() - 1);
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_style, null);
                ((TextView) convertView.findViewById(R.id.addressTV)).setText(orderDto.getPoints());
                ((TextView) convertView.findViewById(R.id.whenTV)).setText(simpleDateFormat.format(orderDto.getTime()));
            }
            catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }
            return convertView;
        }

    }

