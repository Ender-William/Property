package com.kkpip2022.property.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kkpip2022.property.R;
import com.kkpip2022.property.activity.history_operate;
import com.kkpip2022.property.api.SharedPreferenceDefault;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends BaseFragment {

    TextView name_tv;
    TextView stuID_tv;

    ImageButton history_btn;

    // TODO: Rename parameter arguments, choose names that matc
    public MeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me,container,false);

        name_tv = view.findViewById(R.id.me_name_tv);
        stuID_tv = view.findViewById(R.id.me_stuid_tv);

        history_btn = view.findViewById(R.id.me_history_ibtn);

        String Name = GetStringSharedPreferencesContains(
                SharedPreferenceDefault.SharedPreferenceSysConfName,
                SharedPreferenceDefault.SharedPreferenceSysConfigLoginName
        );

        String stuID = GetStringSharedPreferencesContains(
                SharedPreferenceDefault.SharedPreferenceSysConfName,
                SharedPreferenceDefault.SharedPreferenceSysConfigLoginStuID
        );

        name_tv.setText(Name);

        stuID_tv.setText(stuID);

        history_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateTo(history_operate.class);
            }
        });

        return view;
    }
}