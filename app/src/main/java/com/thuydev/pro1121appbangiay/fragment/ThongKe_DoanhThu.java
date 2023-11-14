package com.thuydev.pro1121appbangiay.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thuydev.pro1121appbangiay.R;

import java.util.Calendar;


public class ThongKe_DoanhThu extends Fragment {

    EditText edt_NgayEnd, edt_NgayStart;
    TextView tv_doanhThu,tv_ChoNgayEnd, tv_chonNgayStart;

    public ThongKe_DoanhThu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke__doanh_thu, null);
        edt_NgayEnd = view.findViewById(R.id.edt_ngayEnd);
        edt_NgayStart = view.findViewById(R.id.edt_ngayStart);
        tv_chonNgayStart = view.findViewById(R.id.tv_chonNgayStart);
        tv_ChoNgayEnd = view.findViewById(R.id.tv_chonNgayEnd);

        tv_doanhThu = view.findViewById(R.id.tv_doanhThu);

        Calendar calendar = Calendar.getInstance();
        tv_chonNgayStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if (dayOfMonth < 10) {
                            ngay = "0" + dayOfMonth;
                        }else{
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if(month<10){
                            thang="0"+month;
                        }else {
                            thang = String.valueOf(month);
                        }
                        edt_NgayStart.setText(year+"/"+thang+"/"+ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONDAY),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        tv_ChoNgayEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if(dayOfMonth<10){
                            ngay = "0"+dayOfMonth;
                        }else{
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if(month<10){
                            thang = "0"+month;
                        }else {
                            thang = String.valueOf(month);
                        }
                        edt_NgayEnd.setText(year+"/"+thang+"/"+ngay);
                    }
                },
                        calendar.get(calendar.YEAR),
                        calendar.get(calendar.MONDAY),
                        calendar.get(calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        return view;
    }
}