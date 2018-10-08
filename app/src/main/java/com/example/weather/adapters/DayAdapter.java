package com.example.weather.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weather.R;
import com.example.weather.api.api_models.ForecastDay;
import com.example.weather.main_activity.presenter.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    private Context context;
    private RecyclerItemClickListener recyclerItemClickListener;
    private ArrayList<ForecastDay> forecastDays;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public DayAdapter(Context context, ArrayList<ForecastDay> forecastDays, RecyclerItemClickListener recyclerItemClickListener) {
        this.context = context;
        this.forecastDays = forecastDays;
        this.recyclerItemClickListener = recyclerItemClickListener;
        inflater = LayoutInflater.from(context);
    }

    private LayoutInflater inflater;

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.day_card_view, parent, false);

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (parent.getWidth() * 0.35);
        view.setLayoutParams(layoutParams);

        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DayViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.forecastCardView.setOnClickListener(v -> recyclerItemClickListener.onItemClick(forecastDays.get(position)));

        Picasso.get().load(context.getString(R.string.http).concat(forecastDays.get(position).getDay().getCondition().getIcon())).into(holder.forecastImageView);

        holder.maxTemperatureTextView.setText(String.format(Locale.ENGLISH, context.getString(R.string.celsius), String.format(Locale.ENGLISH, context.getString(R.string.double_format), forecastDays.get(position).getDay().getMaxTemperature())));

        holder.minTemperatureTextView.setText(String.format(Locale.ENGLISH, context.getString(R.string.celsius), String.format(Locale.ENGLISH, context.getString(R.string.double_format), forecastDays.get(position).getDay().getMinTemperature())));

        holder.forecastWeatherTextView.setText(forecastDays.get(position).getDay().getCondition().getTemperatureCondition());

        try {
            holder.dayTextView.setText(new SimpleDateFormat(context.getString(R.string.day_date_format), Locale.ENGLISH).format(simpleDateFormat.parse(forecastDays.get(position).getDate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return forecastDays.size();
    }

    class DayViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.forecastCardView)
        CardView forecastCardView;
        @BindView(R.id.dayTextView)
        TextView dayTextView;
        @BindView(R.id.forecastImageView)
        ImageView forecastImageView;
        @BindView(R.id.maxTemperatureTextView)
        TextView maxTemperatureTextView;
        @BindView(R.id.minTemperatureTextView)
        TextView minTemperatureTextView;
        @BindView(R.id.forecastWeatherTextView)
        TextView forecastWeatherTextView;

        @SuppressLint("ClickableViewAccessibility")
        DayViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            forecastCardView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in);
//                    forecastCardView.startAnimation(anim);
//                    anim.setFillAfter(true);
//                    return false;
//                }
//            });
//
//            Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out);
//            forecastCardView.startAnimation(anim);
//            anim.setFillAfter(true);

        }
    }
}


