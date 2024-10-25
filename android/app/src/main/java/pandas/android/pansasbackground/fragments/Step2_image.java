package pandas.android.pansasbackground.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;

import pandas.android.pansasbackground.Helper.Image;
import pandas.android.pansasbackground.Helper.general;
import pandas.android.pansasbackground.R;
import pandas.android.pansasbackground.Services.Uploader;

public class Step2_image extends Fragment {
    Context current_context;
    String outputImagePAthStep1, found_ojects, selected_uri_string, back_uri_string,effect;
    int blur_radius_int;
    LinearLayout things_layout;
    ImageView showImage;
    Button next;
    ArrayList<CheckBox> checkBoxes_elements=new ArrayList<CheckBox>();;
    ConstraintLayout main;

    public Step2_image() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View main_inflate = inflater.inflate(R.layout.fragment_step2_image, container, false);
        current_context = this.getContext();
        getArgs();
        setLayouts(main_inflate);
        setImageView();
        animateLayout();
        setCheckBoxes();
        setNextButton();
        return main;
    }

    private void setNextButton() {
        next.setOnClickListener(view -> {
            ArrayList<String> checkedItems = new ArrayList<>();

            // Loop through each CheckBox and check if it's checked
            for (CheckBox checkBox : checkBoxes_elements) {
                if (checkBox.isChecked()) {
                    checkedItems.add(checkBox.getText().toString());
                }
            }
            AlertDialog dialog=general.prepareSmallAlertDialog(current_context,R.drawable.sandclock,R.string.load,false);
            call_server_step2(dialog,checkedItems);


        });
    }
    private void animateLayout() {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim2);
        main.startAnimation(animation);
    }
    private void call_server_step2(AlertDialog dialog,ArrayList<String> checkedItems) {

        Data inputData = new Data.Builder()
                .putString("effect", effect)
                .putString("selected_image_uri",selected_uri_string)
                .putString("selected_image_uri",selected_uri_string)
                .putString("back_image_uri",back_uri_string)
                .putInt("blur_radius",blur_radius_int)
                .putString("Step","Step2_image")
                .putString("found_objects",general.getPythonArr(checkedItems))
                .build();


        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(Uploader.class)
                .setInputData(inputData)
                .build();


        WorkManager.getInstance(current_context).enqueue(myWorkRequest);

        WorkManager.getInstance(current_context).getWorkInfoByIdLiveData(myWorkRequest.getId())
                .observe((LifecycleOwner) current_context, workInfo -> {
                    if (workInfo != null && workInfo.getState().isFinished()) {
                        dialog.dismiss();
                        if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Toast.makeText(current_context, "Upload successful!", Toast.LENGTH_SHORT).show();

                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            Toast.makeText(current_context, "Upload failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setCheckBoxes() {
        String[] image_elements = general.getJavaArray(found_ojects);

        for (String imageElement : image_elements) {
            CheckBox checkBox_i = new CheckBox(current_context);
            checkBox_i.setText(imageElement);
            formatCheckBox(checkBox_i);
            checkBox_i.setChecked(false);

            checkBoxes_elements.add(checkBox_i);
            things_layout.addView(checkBox_i);

        }
    }

    private void formatCheckBox(CheckBox elementBox) {

        elementBox.setTextSize(20);
        elementBox.setTextColor(0xFF5588FF);

        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(20, 8, 20, 8);
        elementBox.setLayoutParams(layoutParams);
    }

    private void setLayouts(View mainInflate) {
        things_layout = mainInflate.findViewById(R.id.detected_list);
        next=mainInflate.findViewById(R.id.next_step2);
        showImage = mainInflate.findViewById(R.id.mainPic);
        main= mainInflate.findViewById(R.id.frameLayout);

    }

    private void setImageView() {


        String encoded = Image.readBase64FromFile(outputImagePAthStep1);
        Bitmap bitmap = Image.decodeBase64ToBitmap(encoded);
        showImage.setImageBitmap(bitmap);


    }

    private void getArgs() {
        if (getArguments() != null) {
            effect=getArguments().getString(Step1_image.effect_key);
            outputImagePAthStep1 = getArguments().getString("SimageFilePath");
            found_ojects = getArguments().getString("found_ojects");
            selected_uri_string = getArguments().getString("selected_uri_string");
            back_uri_string = getArguments().getString("back_uri_string");
            blur_radius_int = getArguments().getInt("blur_radius_int");
        }
    }
}