package com.example.sherifcalendar;



import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context ;
    private ArrayList<CalendarClass> dayList;
    private OnEntryClickListener mOnEntryClickListener ;
    private Globals g ;
    private int lastPosition = 0 ;
    private String from ="", to = "" , start_date ="",end_date ="";
    private boolean enableRange = false ;

    Adapter(ArrayList<CalendarClass> dayList, Context context) {
        this.dayList = dayList;
        this.context = context ;
        g = new Globals(context);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row, parent, false);
            return new ItemViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            populateItemRows((ItemViewHolder) holder, position);

    }


    @Override
    public int getItemCount() {
        return dayList == null ? 0 : dayList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView day ;

        ItemViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            day.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
          CalendarClass  calendarClass = dayList.get(getLayoutPosition()) ;
          int position = getLayoutPosition();
            boolean isCrossRange = true; // check if range contain blocked days
            for (int i = lastPosition; i <= position; i++) {
                if (!dayList.get(i).isAvailable()) {
                    isCrossRange = false;
                    break;
                }
            }

            if (calendarClass.isDay() && calendarClass.isAvailable()) {
                calendarClass.setChecked(true);
                String date = calendarClass.getFullDate();
                if (from.length() == 0) {
                    from = date;
                    start_date = date;
                    end_date = "";
                    // g.myToast(from + to);
                    lastPosition = position;
                    dayList.get(position).setChecked(true);
                    for (int i = 0; i < dayList.size(); i++) {
                        if (i != position) {
                            dayList.get(i).setChecked(false);
                        }
                    }
                } else if (to.length() == 0 && position > lastPosition && isCrossRange && enableRange) {
                    to = date;
                    end_date = date;
                    //  g.myToast(from + to);
                    // add background in range
                    for (int i = lastPosition; i < position; i++) {
                        dayList.get(i).setInRange(true);
                    }
                    //end part
                    lastPosition = position;
                    dayList.get(position).setChecked(true);
                } else {
                    to = "";
                    end_date = "";
                    from = date;
                    start_date = date;
                    // g.myToast(from + to);
                    lastPosition = position;
                    for (int i = 0; i < dayList.size(); i++) {
                        if (i != position) {
                            dayList.get(i).setChecked(false);
                            dayList.get(i).setInRange(false);
                        }
                    }
                }
                notifyDataSetChanged();
            } else if (!calendarClass.isAvailable() && calendarClass.isDay()) {
                g.myToast("لا يمكن الحجز في هذا التاريخ");
            }

            if(mOnEntryClickListener!=null){
                mOnEntryClickListener.onEntryClick(v,getLayoutPosition() , from , to);

            }
        }
    }
    public interface OnEntryClickListener{
        void onEntryClick(View view, int position ,String from , String to);
    }
    void setmOnEntryClickListener(OnEntryClickListener onEntryClickListener){
        mOnEntryClickListener=onEntryClickListener;
    }
    private void   populateItemRows(ItemViewHolder holder,int position) {
        CalendarClass testClass = dayList.get(position);
        holder.day.setText(testClass.getDayTxt());
        if (isDivid7(position)&&testClass.getDayTxt().length()==0){
            holder.day.setText(testClass.getTitle());
            holder.day.setTextSize(TypedValue.COMPLEX_UNIT_SP,18f);
        }else{
            holder.day.setText(testClass.getDayTxt());
            holder.day.setTextSize(TypedValue.COMPLEX_UNIT_SP,12f);
        }
        if (testClass.isAvailable()) {
            if (testClass.isChecked()) {
                holder.day.setBackground(g.blueShape60());
                holder.day.setTextColor(context.getResources().getColor(R.color.mywhite));
            } else if (testClass.isInRange()) {
                holder.day.setBackground(g.shape(R.color.myDarkBlue, 0,0,0));
            } else {
                holder.day.setBackground(null);
                holder.day.setTextColor(context.getResources().getColor(R.color.textBlackColor));
            }
        }else if (testClass.isDay()){
            holder.day.setBackground(null);
            holder.day.setTextColor(context.getResources().getColor(R.color.mydarkgray));
        }else{
            holder.day.setTextColor(context.getResources().getColor(R.color.textBlackColor));

        }
    }
    private boolean isDivid7(int number){
        int x = number/7;
        int y = x*7 ;
        return y == number;
    }

}