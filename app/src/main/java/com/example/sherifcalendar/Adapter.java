package com.example.sherifcalendar;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
            if(mOnEntryClickListener!=null){
                mOnEntryClickListener.onEntryClick(v,getLayoutPosition() , dayList.get(getLayoutPosition()));

            }
        }
    }
    public interface OnEntryClickListener{
        void onEntryClick(View view, int position , CalendarClass calendarClass);
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
                holder.day.setBackground(g.shapeColorString("#310090FF", 0));
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