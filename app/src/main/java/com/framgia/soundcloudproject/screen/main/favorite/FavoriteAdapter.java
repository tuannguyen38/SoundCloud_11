package com.framgia.soundcloudproject.screen.main.favorite;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.screen.main.BaseTrackAdapter;
import com.framgia.soundcloudproject.screen.main.TrackListener;

import java.util.List;

public class FavoriteAdapter extends BaseTrackAdapter<FavoriteAdapter.FavoriteViewHolder> {

    public FavoriteAdapter(Context context, TrackListener trackListener) {
        super(context, trackListener);
    }

    static class FavoriteViewHolder extends BaseTrackAdapter.BaseViewHolder {

        public FavoriteViewHolder(View itemView, List<Track> tracks, TrackListener trackListener) {
            super(itemView, tracks, trackListener);
        }

        @Override
        protected void setupUI(View view) {
            mImageViewFavorite.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void handleOptions() {
            PopupMenu popupMenu = new PopupMenu(mContext, mImageViewOptions);
            popupMenu.inflate(R.menu.menu_item_favorite);
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_play:
                            handlePlayTrack();
                            return true;
                        case R.id.action_remove:
                            // TODO delete track
                            return true;
                        default:
                            return false;
                    }
                }
            });
            popupMenu.show();
        }

        private void handlePlayTrack() {
            if (mTrackListener == null) return;
            mTrackListener.onPlayTrack(getAdapterPosition(), mTracks);
        }
    }
}
