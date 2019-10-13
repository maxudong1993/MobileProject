package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import com.example.project.utils.nav_bar;

public class ProfileFragment extends Fragment {
    @BindView(R.id.h_back)
    ImageView hBack;
    @BindView(R.id.h_head)
    ImageView hHead;
    @BindView(R.id.user_line)
    ImageView userLine;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_val)
    TextView userVal;
    @BindView(R.id.lishi)
    nav_bar lishi;
    @BindView(R.id.shoucang)
    nav_bar shoucang;
    @BindView(R.id.version)
    nav_bar version;
    @BindView(R.id.about)
    nav_bar about;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.about,R.id.version,R.id.lishi,R.id.shoucang})
    public void onViewClicked(View v){
        switch (v.getId()){
            case R.id.about:
                System.out.println("about yoyoyoyoyoyoy");
                Toast.makeText(getActivity(), "about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.version:
                Toast.makeText(getActivity(), "v", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lishi:
                Toast.makeText(getActivity(), "lishi", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shoucang:
                Toast.makeText(getActivity(), "shoucang", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
