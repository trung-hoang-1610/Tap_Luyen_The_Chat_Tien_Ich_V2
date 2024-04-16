package com.example.tap_luyen_the_chat_tien_ich_v2.Views.Fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tap_luyen_the_chat_tien_ich_v2.Adapter.PlanAdapter;
import com.example.tap_luyen_the_chat_tien_ich_v2.ModelDAO.Plan.PlanDAO;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.User;
import com.example.tap_luyen_the_chat_tien_ich_v2.Models.plan.Plan;
import com.example.tap_luyen_the_chat_tien_ich_v2.R;
import com.example.tap_luyen_the_chat_tien_ich_v2.Views.Activities.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlansListFragment extends Fragment{

    PlanDAO planDAO;
    private RecyclerView recyclerView;
    private PlanAdapter adapter;
    private PopupWindow popupWindow;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private ImageView notificationButton;
    private List<Plan> data = new ArrayList<>();
    private View mView;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_plans_list, container, false);
        recyclerView = mView.findViewById(R.id.rcView);
        notificationButton = mView.findViewById(R.id.btnNotification);
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            user = activity.getUser();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        planDAO = new PlanDAO();
       // myRef.child("User_Plan").push().child("planId").setValue();
        myRef.child("User_Plan").child(user.getId()).child("planIds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                data.clear();


                planDAO.getAllPlans(new PlanDAO.PlansCallback() {
                    @Override
                    public void onPlanListReceived(List<Plan> planList) {
                        if(planList != null){

                                for(Plan plan : planList){
                                    for(DataSnapshot child : snapshot.getChildren()){

                                        if(plan.getId().equals(child.getKey())){
                                            data.add(plan);
                                        }
                                    }

                                }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); // Change this line
                            adapter = new PlanAdapter(getContext(), data); // Change this line
                            recyclerView.setAdapter(adapter);
                        }
                        else{
                            System.out.println("Không có dữ liệu về plan!");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);


        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temperatureSensor == null) {
                    // Thiết bị không hỗ trợ cảm biến nhiệt độ
                    Toast.makeText(getActivity(), "Thiết bị của bạn không hỗ trợ cảm biến nhiệt độ", Toast.LENGTH_SHORT).show();
                } else {
                    // Đăng ký lắng nghe sự kiện từ cảm biến nhiệt độ
                    sensorManager.registerListener(sensorEventListener, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
                }

                // Xóa chấm màu đỏ khi button được nhấn
                notificationButton.setBackgroundResource(R.drawable.ic_bell);
            }
        });



        return mView;
    }

    private final SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            float temperature = event.values[0];
            String message = "\nNhiệt độ hiện tại: " + temperature +"℃" +"\nGợi ý tập luyện: ";
            if (temperature > 20) {
                // Nhiệt độ lớn hơn 20 độ, hiển thị PopupWindow gợi ý mặc áo ngắn
                showPopup(message +"Tránh tập luyện ngoài trời vào giờ nắng gắt. Hãy tập trong nhà hoặc tại các phòng tập có nhiệt độ mát mẻ!");
                sensorManager.unregisterListener(sensorEventListener);
            } else {
                // Nhiệt độ nhỏ hơn hoặc bằng 20 độ, hiển thị PopupWindow gợi ý mặc áo ấm
                showPopup(message + "Trới khá lạnh, hãy cân nhắc sử dụng thêm đồ ấm và khởi động kỹ khi tập luyện ngoài trời và hãy chú ý giữ cơ thể ấm khi thực hiện các bài tập!");
                sensorManager.unregisterListener(sensorEventListener);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Không cần xử lý ở đây
        }
    };
    TextView txtMessage;
    private void showPopup(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View popupView = inflater.inflate(R.layout.popup_notification, null);
        txtMessage = popupView.findViewById(R.id.txtMessage);
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
        notificationButton.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        // Lấy kích thước của Button
        int buttonWidth = notificationButton.getWidth();
        int buttonHeight = notificationButton.getHeight();

        // Lấy kích thước của PopupWindow
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popupView.getMeasuredWidth();
        int popupHeight = popupView.getMeasuredHeight();

        // Tính toán vị trí x, y của PopupWindow
        int popupX = x + (buttonWidth - popupWidth) / 2;
        int popupY = y + buttonHeight;

        // Hiển thị PopupWindow ở vị trí mong muốn
        popupWindow.showAtLocation(notificationButton, Gravity.START | Gravity.TOP, popupX, popupY);

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
    public void onDestroyView() {
        super.onDestroyView();
        // Hủy đăng ký lắng nghe sự kiện từ cảm biến nhiệt độ khi Fragment không còn được hiển thị
        sensorManager.unregisterListener(sensorEventListener);
    }
}