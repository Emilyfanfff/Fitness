package com.example.fitnessapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.databinding.ReportFragmentBinding;
import com.example.fitnessapp.entity.SportData;
import com.example.fitnessapp.viewmodel.SportViewModel;
import com.facebook.CallbackManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

public class ReportFragment extends Fragment {
    private ReportFragmentBinding binding;
    private SportViewModel sportViewModel;
    private String startDate;
    private String endDate;
    PieChart pieChart;

    List<PieEntry> pieEntryList;

    CallbackManager callbackManager;
    ShareButton sb;
    public ReportFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the View for this fragment using the binding
        binding = ReportFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentUser.getUid();


        binding.startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select start date");

                MaterialDatePicker<Long> materialDatePicker = builder.build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selectedDate) {
                        // 处理用户选择的日期
                        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
                        String newStartDate = format.format(new Date(selectedDate));
                        startDate = newStartDate;
                    }
                });
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        binding.endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                builder.setTitleText("Select end date");

                MaterialDatePicker<Long> materialDatePicker = builder.build();

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selectedDate) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy MM dd");
                        String newEndDate = format.format(new Date(selectedDate));
                        endDate = newEndDate;
                        showChart(uid,view,startDate,endDate);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setShareContent();
                            }
                        }, 3000);
                    }
                });

                materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });

        return view;
    }

    private void setUpChart() {
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"Pie Chart");
        PieData pieData = new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData.setValueTextSize(12f);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void showChart(String uid,View view,String startDate,String finalDate){
        sportViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())
                .create(SportViewModel.class);
        CompletableFuture<SportData> dataCompletableFuture = sportViewModel.findByIDFuture(uid);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            dataCompletableFuture.thenApply(data -> {
                if(data != null){
                    String date1 = startDate;
                    String date2 = finalDate;

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
                        Date beginDate;
                        Date endDate;
                        try {
                            beginDate = df.parse(date1);
                            endDate = df.parse(date2);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Double totalMin = 0.0;
                        Double yogaMin = 0.0;
                        Double swimmingMin = 0.0;
                        Double joggingMin = 0.0;
                        Double boxingMin = 0.0;
                        Double rope_skippingMin = 0.0;
                        int startIndex = -1, endIndex = -1;
                        for (int i = 0; i < data.date.size(); i++) {
                            Date current;
                            try {
                                current = df.parse(data.date.get(i));
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            if (startIndex == -1 && (current.after(beginDate) || current.equals(beginDate))) {
                                startIndex = i;
                            }

                            if (current.before(endDate) || current.equals(endDate)) {
                                endIndex = i;
                            }
                        }
                        for(int i = startIndex;i <= endIndex;i++){
                            totalMin += data.daily_minute.get(i);
                        }
                        for (int i = startIndex;i <= endIndex;i++){
                            yogaMin += data.yoga.get(i);
                        }
                        for (int i = startIndex;i <= endIndex;i++){
                            swimmingMin += data.swimming.get(i);
                        }
                        for (int i = startIndex;i <= endIndex;i++){
                            joggingMin += data.jogging.get(i);
                        }
                        for (int i = startIndex;i <= endIndex;i++){
                            boxingMin += data.boxing.get(i);
                        }
                        for (int i = startIndex;i <= endIndex;i++){
                            rope_skippingMin += data.rope_skipping.get(i);
                        }

                        Log.i("totalMin", String.valueOf(totalMin));
                        Log.i("yogaMin", String.valueOf(yogaMin));
                        Log.i("swimmingMin", String.valueOf(swimmingMin));
                        Log.i("joggingMin", String.valueOf(joggingMin));

                        pieEntryList = new ArrayList<>();

                        pieChart = view.findViewById(R.id.chart);
                        setValues(yogaMin,swimmingMin,joggingMin,boxingMin,rope_skippingMin);

                        setUpChart();
                        Log.i("11111111111", String.valueOf(pieEntryList.size()));
                        List<BarEntry> barEntries = new ArrayList<>();
                        barEntries.add(new BarEntry(0, Float.parseFloat(String.valueOf(yogaMin))));
                        barEntries.add(new BarEntry(1, Float.parseFloat(String.valueOf(swimmingMin))));
                        barEntries.add(new BarEntry(2, Float.parseFloat(String.valueOf(joggingMin))));
                        barEntries.add(new BarEntry(3, Float.parseFloat(String.valueOf(boxingMin))));
                        barEntries.add(new BarEntry(4, Float.parseFloat(String.valueOf(rope_skippingMin))));

                        BarDataSet barDataSet = new BarDataSet(barEntries, "sports");
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                        List<String> xAxisValues = new ArrayList<>(Arrays.asList("yoga", "swimming", "jogging", "boxing", "rope skipping"));
                        binding.barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
                        BarData barData = new BarData(barDataSet);
                        binding.barChart.setData(barData);
                        barData.setBarWidth(1.0f);
                        binding.barChart.setVisibility(View.VISIBLE);
                        binding.barChart.animateY(4000);

                        //description will be displayed as "Description Label" if not provided
                        Description description = new Description();
                        description.setText("");
                        binding.barChart.setDescription(description);

                        //refresh the chart
                        binding.barChart.invalidate();


                    }


                }
                return data;
            });
        }
    }

    private void setValues(double yoga,double swimming, double jogging,double boxing, double rope_skipping) {
        if(yoga > 0) pieEntryList.add(new PieEntry((float) yoga,"Yoga"));
        if(swimming > 0) pieEntryList.add(new PieEntry((float) swimming,"Swimming"));
        if(jogging > 0) pieEntryList.add(new PieEntry((float) jogging,"Jogging"));
        if(boxing > 0) pieEntryList.add(new PieEntry((float) boxing,"boxing"));
        if(rope_skipping > 0) pieEntryList.add(new PieEntry((float) rope_skipping,"rope_skipping"));
    }

    public void setShareContent(){
        sb = binding.shareBtn;
        callbackManager = CallbackManager.Factory.create();

        View rootView = requireActivity().getWindow().getDecorView().getRootView();
        rootView.setDrawingCacheEnabled(true);
        Bitmap screenshot = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(screenshot)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        sb.setShareContent(content);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
