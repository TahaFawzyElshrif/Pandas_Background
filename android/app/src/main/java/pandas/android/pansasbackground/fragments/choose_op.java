package pandas.android.pansasbackground.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import pandas.android.pansasbackground.Helper.general;
import pandas.android.pansasbackground.R;


public class choose_op extends Fragment {
    LinearLayout img_cam ,img_replace ,img_remove,vid_cam,vid_replace,vid_remove,vidvid;
    ConstraintLayout main;
    Context current_context;
    public choose_op() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        current_context=this.getContext();
        View inflated_view= inflater.inflate(R.layout.fragment_choose_op, container, false);
        intialize(inflated_view);
        animateLayout();
        setButtons();

        return inflated_view;
    }

    private void animateLayout() {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim2);
        main.startAnimation(animation);
    }

    private void setButtons() {
        setButton(img_cam,Step1_image.effect_cam);
        setButton(img_replace,Step1_image.effect_replace);
        setButton(img_remove,Step1_image.effect_remove);
        setButton(vid_cam,"vid_cam");
        setButton(vid_replace,"vid_replace");
        setButton(vid_remove,"vid_remove");
        setButton(vidvid,"VideoByVideo");
    }


    private void setButton(LinearLayout button,String effect) {
        button.setOnClickListener(view -> goToIntendedFragment(effect));

        button.setOnLongClickListener(v -> {
            animateButton(button);
            return true; // Indicate that the event has been handled
        });

    }

    private void goToIntendedFragment(String effect) {
        if (effect.equals(Step1_image.effect_remove) || effect.equals(Step1_image.effect_cam) || effect.equals(Step1_image.effect_replace)) {
            Step1_image fragmentB = new Step1_image(); //fragment

            Bundle bundle = new Bundle();
            bundle.putString(Step1_image.effect_key, effect);

            fragmentB.setArguments(bundle);

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(getId(), fragmentB);
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            general.prepareSmallAlertDialog(current_context,R.drawable.inprogress,R.string.later_version,true);
        }

    }

    private void animateButton(LinearLayout button) {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim2);
        animation.setRepeatCount(8);
        button.startAnimation(animation);
    }

    private void intialize(View inflated_view) {
        img_cam=inflated_view.findViewById(R.id.img_cam);
        img_replace=inflated_view.findViewById(R.id.img_replace);
        img_remove=inflated_view.findViewById(R.id.img_remove);
        vid_cam=inflated_view.findViewById(R.id.vid_cam);
        vid_replace=inflated_view.findViewById(R.id.vid_replace);
        vid_remove=inflated_view.findViewById(R.id.vid_remove);
        main= inflated_view.findViewById(R.id.main);
        vidvid=inflated_view.findViewById(R.id.vid_vid);
    }
}