package com.framgia.soundcloudproject.screen.main.playtrack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.constant.Constant;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hades on 8/19/2018.
 */
public class NextUpAdapter extends RecyclerView.Adapter<NextUpAdapter.NextUpViewHolder> {

    private OnTrackItemClickListener mListener;
    private int mCurrentPosition;
    private List<Track> mTracks;
    private LayoutInflater mInflater;
    private Context mContext;

    public NextUpAdapter(Context context, int currentPosition, OnTrackItemClickListener listener) {
        mContext = context;
        mCurrentPosition = currentPosition;
        mListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NextUpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.item_track_next_up, parent, false);
        return new NextUpViewHolder(rootView, mContext, mTracks, mCurrentPosition, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NextUpViewHolder holder, int position) {
        holder.bindData(position);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class NextUpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImvTrack;
        private ImageView mImvSelected;
        private TextView mTvTitle;
        private TextView mTvArtist;
        private TextView mTvDuration;
        private List<Track> mTracks;
        private int mCurrentPosition;
        private Context mContext;
        private OnTrackItemClickListener mListener;

        public NextUpViewHolder(View itemView, Context context, List<Track> tracks,
                                int currentPosition, OnTrackItemClickListener listener) {
            super(itemView);
            this.mTracks = tracks;
            this.mCurrentPosition = currentPosition;
            this.mListener = listener;
            this.mContext = context;
            mImvTrack = itemView.findViewById(R.id.image_next_up_track);
            mImvSelected = itemView.findViewById(R.id.image_next_up_selected);
            mTvTitle = itemView.findViewById(R.id.text_next_up_title);
            mTvArtist = itemView.findViewById(R.id.text_next_up_artist);
            mTvDuration = itemView.findViewById(R.id.text_next_up_duration);
            itemView.setOnClickListener(this);
        }

        public void bindData(int position) {
            if (mTracks == null) return;
            if (mCurrentPosition == getAdapterPosition()) {
                mImvSelected.setVisibility(View.VISIBLE);
            } else {
                mImvSelected.setVisibility(View.GONE);
            }
            mTvTitle.setText(mTracks.get(position).getTitle());
            mTvArtist.setText(mTracks.get(position).getPublisherAlbumTitle());
            mTvDuration.setText(StringUtil.convertMilisecondToTimer(mTracks.get(position).getFullDuration()));
            Glide.with(mContext).load(mTracks.get(position).getArtworkUrl())
                    .apply(new RequestOptions().override(Constant.DEFAULT_ITEM_SIZE).error(R.drawable.ic_holder))
                    .into(mImvTrack);
        }

        @Override
        public void onClick(View v) {
            if (mListener == null) return;
            mCurrentPosition = getAdapterPosition();
            mListener.onTrackItemClicked(mCurrentPosition);
        }
    }

    public void updateListTrack(List<Track> tracks) {
        if (tracks == null) return;
        if (mTracks == null) mTracks = new ArrayList<>();
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    interface OnTrackItemClickListener {
        void onTrackItemClicked(int position);
    }
}
