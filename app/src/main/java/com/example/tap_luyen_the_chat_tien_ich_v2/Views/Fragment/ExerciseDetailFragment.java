package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.SimpleExoPlayer;
import androidx.media3.ui.PlayerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.tap_luyen_the_chat_tien_ich_v2.Models.Exercise;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;

import java.util.Objects;


public class ExerciseDetailFragment extends Fragment {
    private Exercise exercise;
    private PlayerView playerView;
    ExoPlayer player;
    private PopupWindow popupWindow;
    TextView txtExerciseName;
    TextView txtExerciseRep;
    ImageView btnInfor;
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public ExerciseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View mView = inflater.inflate(R.layout.fragment_exercise_detail, container, false);
        playerView = mView.findViewById(R.id.playerView);
        txtExerciseName = mView.findViewById(R.id.txtExerciseName);
        txtExerciseRep = mView.findViewById(R.id.txtExerciseRep);
        btnInfor = mView.findViewById(R.id.btnInfor);

        txtExerciseName.setText(exercise.getExcercise_name());
        txtExerciseRep.setText(exercise.getExcercise_description());
        System.out.println(exercise.getExcercise_description() +"HHHHH");
        player = new ExoPlayer.Builder(requireContext()).build();
        playerView.setPlayer(player);
        MediaItem mediaItem = MediaItem.fromUri(exercise.getExcercise_path());
        player.setMediaItem(mediaItem);
        player.prepare();
        player.setPlayWhenReady(true);
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    // Phát lại khi video kết thúc
                    player.seekTo(0); // quay lại điểm bắt đầu
                    player.setPlayWhenReady(true);
                }
            }
        });

        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(exercise.getExcercise_description());
            }
        });
        if(exercise.getExcercise_name().contains("push-ups")){

        }
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Kiểm tra nếu video đã kết thúc thì phát lại
        if(player != null){
            player.setPlayWhenReady(true);

        }else{
            player = new ExoPlayer.Builder(requireContext()).build();
            playerView.setPlayer(player);
            MediaItem mediaItem = MediaItem.fromUri(exercise.getExcercise_path());
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(true);
            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_ENDED) {
                        // Phát lại khi video kết thúc
                        player.seekTo(0); // quay lại điểm bắt đầu
                        player.setPlayWhenReady(true);
                    }
                }
            });
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            player.release();
        }
    }
    TextView txtMessage;
    private void showPopup(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_notification, null);
        txtMessage = popupView.findViewById(R.id.txtCurrentTemp);
        txtMessage.setText(message);
        // Tạo PopupWindow
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // Cho phép PopupWindow nhận sự kiện touch bên ngoài để đóng cửa sổ
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Cấu hình PopupWindow
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setElevation(20);
        // Tính toán vị trí của Button "notification" trên màn hình
        int[] location = new int[2];
        btnInfor.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        // Lấy kích thước của Button
        int buttonWidth = btnInfor.getWidth();
        int buttonHeight = btnInfor.getHeight();

        // Lấy kích thước của PopupWindow
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popupView.getMeasuredWidth();
        int popupHeight = popupView.getMeasuredHeight();

        // Tính toán vị trí x, y của PopupWindow
        int popupX = x + (buttonWidth - popupWidth) / 2;
        int popupY = y + buttonHeight;

        // Hiển thị PopupWindow ở vị trí mong muốn
        popupWindow.showAtLocation(btnInfor, Gravity.START | Gravity.TOP, popupX, popupY);

        // Đặt sự kiện để đóng PopupWindow khi click bên ngoài
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.setPlayWhenReady(false);
            player.release();
            player = null;
        }
    }
}