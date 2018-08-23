package com.framgia.soundcloudproject.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.framgia.soundcloudproject.R;
import com.framgia.soundcloudproject.data.model.Playlist;
import com.framgia.soundcloudproject.data.model.Track;
import com.framgia.soundcloudproject.data.repository.TrackRepository;
import com.framgia.soundcloudproject.data.source.TrackDataSource;
import com.framgia.soundcloudproject.screen.main.playlist.PlaylistDialogAdapter;

import java.util.List;

public class DialogManager implements DialogInterface {

    private Context mContext;
    private AlertDialog mDialog;

    public DialogManager(Context context) {
        mContext = context;
    }

    @Override
    public void showMessageDialog(String title, String msg) {
        if(mContext != null) {
            mDialog = new AlertDialog.Builder(mContext)
                    .setTitle(title)
                    .setMessage(msg)
                    .create();
            mDialog.show();
        }
    }

    @Override
    public void showAddToPlaylistDialog(final Activity activity, final Track track) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_playlist_selection, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(mContext.getString(R.string.action_add_to_playlist));
        final AlertDialog alertDialog = dialogBuilder.create();

        final TrackDataSource.OnQueryDatabaseListener listener =
                new TrackDataSource.OnQueryDatabaseListener() {
                    @Override
                    public void onQuerySuccess(String msg) {
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }

                    @Override
                    public void onQueryFailure(Exception e) {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                };

        final EditText editTextNewPlaylist = dialogView.findViewById(R.id.text_playlist_name);
        ImageView imageAddPlaylist = dialogView.findViewById(R.id.image_add_playlist);
        imageAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackRepository repository = TrackRepository.getInstance();
                Playlist newPlaylist = new Playlist();
                newPlaylist.setName(editTextNewPlaylist.getText().toString());
                repository.insertPlaylist(newPlaylist, listener);
                List<Playlist> playlists = repository.getPlaylist();
                Playlist addedPlaylist = playlists.get(playlists.size()-1);
                repository.addTrackToPlaylist(addedPlaylist.getId(), listener, track);
            }
        });

        RecyclerView recyclerView = dialogView.findViewById(R.id.recycler_dialog_playlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new PlaylistDialogAdapter(track, listener));

        alertDialog.show();
    }

    @Override
    public void dismissDialog() {
        if(mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
