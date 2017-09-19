package com.ayocodetest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayocodetest.R;
import com.ayocodetest.model.HistoryData;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

/**
 * Created by Victor on 9/19/17.
 */

public class HistoryAdapter extends EmptyAdapter<HistoryAdapter.ViewHolder> {

    RealmResults<HistoryData> historyDatas;

    public HistoryAdapter(Context context) {
        super(context);
    }

    public void setHistoryDatas(RealmResults<HistoryData> historyDatas) {
        this.historyDatas = historyDatas;
        notifyDataSetChanged();
    }

    @Override
    int getCount() {
        return historyDatas == null ? 0 : historyDatas.size();
    }

    @Override
    int getLayout() {
        return R.layout.no_data;
    }

    @Override
    public ViewHolder createStandardViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.history_list_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position) {
        holder.bind(historyDatas.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_weather_icon)
        ImageView ivWeatherIcon;

        @BindView(R.id.tv_weather_results)
        TextView tvWeatherResults;

        @BindView(R.id.tv_weather_details)
        TextView tvWeatherDetails;

        @BindView(R.id.tv_weather_min)
        TextView tvWeatherMin;

        @BindView(R.id.tv_weather_humidty)
        TextView tvWeatherHumidity;

        @BindView(R.id.tv_weather_max)
        TextView tvWeatherMax;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(HistoryData historyData) {
            Glide.with(getContext()).load(String.format(getContext().getString(R.string.weather_icon), historyData.getIconId())).into(ivWeatherIcon);
            tvWeatherResults.setText(String.format(getContext().getString(R.string.weather_result_in_celsius), historyData.getCurrentTemperature()));
            tvWeatherDetails.setText(historyData.getCityName());
            tvWeatherHumidity.setText(String.format(getContext().getString(R.string.weather_humidity_short), historyData.getHumidty()));
            tvWeatherMax.setText(String.format(getContext().getString(R.string.weather_max), historyData.getMaxTemp()));
            tvWeatherMin.setText(String.format(getContext().getString(R.string.weather_min), historyData.getMinTemp()));
        }
    }
}
