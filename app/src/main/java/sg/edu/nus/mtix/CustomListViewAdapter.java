package sg.edu.nus.mtix;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    Context context;

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        ImageView image;
        TextView titleDisplay;
        TextView descriptionDisplay;
        TextView nameDisplay;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // convertView = mInflater.inflate(R.layout.activity_custom_list_view_adapter, parent, false);
            convertView = mInflater.inflate(R.layout.activity_custom_list_view_adapter, null);
            holder = new ViewHolder();
            holder.titleDisplay = (TextView) convertView.findViewById(R.id.textViewTitle);
            holder.descriptionDisplay = (TextView) convertView.findViewById(R.id.textViewDescription);
            holder.nameDisplay = (TextView) convertView.findViewById(R.id.textViewName);
            Typeface custom_font = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(),  "fonts/ProximaNovaSoft-Bold.otf");
            Typeface custom_font2 = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(),  "fonts/ProximaNovaSoft-Regular.otf");
            holder.titleDisplay.setTypeface(custom_font);
            holder.descriptionDisplay.setTypeface(custom_font2);
            holder.image = (ImageView) convertView.findViewById(R.id.imageViewCustom);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.image.destroyDrawingCache();
        holder.image.setImageDrawable(null);
        holder.image.setImageBitmap(null);

        holder.titleDisplay.setText("Title: " + rowItem.getTitle());
        holder.descriptionDisplay.setText(rowItem.getDesc());
        holder.nameDisplay.setText(rowItem.getName());
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(rowItem.getImage(), 0, (rowItem.getImage().length)));

        //holder.image.destroyDrawingCache();
      //  holder.image.destroyDrawingCache();
        //holder.image.setImageDrawable(null);
       // holder.image.setImageBitmap(null);
        holder.image.setImageDrawable(null);
        holder.image.setImageBitmap(null);
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(rowItem.getImage(), 0, (rowItem.getImage().length)));

        holder.image.setImageDrawable(null);
        holder.image.setImageBitmap(null);
        holder.image.setImageBitmap(BitmapFactory.decodeByteArray(rowItem.getImage(), 0, (rowItem.getImage().length)));

        return convertView;
    }
}
