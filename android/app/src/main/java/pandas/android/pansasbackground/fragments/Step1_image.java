package pandas.android.pansasbackground.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.Data;

import java.util.ArrayList;
import java.util.Objects;

import pandas.android.pansasbackground.Classes.item_back;
import pandas.android.pansasbackground.Helper.general;
import pandas.android.pansasbackground.R;
import pandas.android.pansasbackground.Services.Uploader;

public class Step1_image extends Fragment {
    Context current_context;
    public static final String effect_key ="effect";
    public static final String effect_cam ="img_cam";
    public static final String effect_replace ="img_replace";
    public static final String effect_remove ="img_remove";


    ImageView mainPic;
    //FrameLayout uploadButton;
    ActivityResultLauncher<Intent> galleryLauncherMainImage,galleryLauncherBackImage;

    ImageButton upload_main ,upload_back,choose_exist;
    EditText radius;
    TextView radiusLbl ,backLbl,uploaLbl;
    Button next;
    ConstraintLayout main;

    Uri selected_image_uri,back_image_uri;
    String effect;
    public Step1_image() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private void animateLayout() {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim2);
        main.startAnimation(animation);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        current_context=this.getContext();
        View inflation= inflater.inflate(R.layout.fragment_step1_image, container, false);
        assign_layout(inflation);
        assign_gallery_launchers();
        setChooseExist();
        animateLayout();
        setNextButton();


   //     addAnimationImage();
   //     addFrameUploadButton();

        return inflation;

    }

    private void setChooseExist() {
        choose_exist.setOnClickListener(view -> {
            /*
            ///// work put give error in server so won't be supported now

            AlertDialog.Builder builder = new AlertDialog.Builder(current_context);
            LayoutInflater inflater = LayoutInflater.from(current_context);
            View customLayout = inflater.inflate(R.layout.list_choose_back_layout, null);

            builder.setView(customLayout);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();


            ListView listView=customLayout.findViewById(R.id.lsa);

            ArrayList<item_back> list_backs=new ArrayList<item_back>();
            assign_many_layouts(list_backs);


            itemArrayAdapter  item_adapter=new itemArrayAdapter(current_context,R.layout.list_oneitem_choose_exist,list_backs);
            listView.setAdapter(item_adapter);
            listView.setOnItemClickListener((listView1, raw, position, id) -> {
                Toast.makeText(current_context,((item_back) listView1.getAdapter().getItem(position)).getLabel(),Toast.LENGTH_LONG).show();
                item_back selected_item=((item_back) listView1.getAdapter().getItem(position));
                File file_drawale=Image.drawableToFile(current_context,selected_item.getItemIcon()); //item icon is drawable
                //back_image_uri=file_drawale.toURI();
                //Image.openImageFile(file_drawale,current_context);

                back_image_uri=Uri.fromFile(file_drawale);
                //Toast.makeText(current_context,"Selected : "+back_image_uri.toString(),Toast.LENGTH_LONG).show();
                alertDialog.dismiss();
                //Log.d("TAG",back_image_uri.toString());

                //Log.d("TAG",(general.getRealPathFromURI(current_context, back_image_uri)==null)?"NULL":(general.getRealPathFromURI(current_context, back_image_uri)));
                //Image.openImageFile(new File(Objects.requireNonNull(general.getRealPathFromURI(current_context, back_image_uri))),current_context);
                //Log.d("TAG",back_image_uri.getPath());
            });
            */
            general.prepareSmallAlertDialog(current_context,R.drawable.inprogress,R.string.later_version,true);








        });
    }

    private void assign_many_layouts(ArrayList<item_back> list_backs) {// to make data separated from code
        list_backs.add(new item_back("Back 1","Forest",R.drawable.bck1));
        list_backs.add(new item_back("Back 2", "Home route ",R.drawable.bck2));
        list_backs.add(new item_back("Back 3","Street",R.drawable.bvk3));
    }

    private void assign_layout(View inflation) {
        upload_main=inflation.findViewById(R.id.image_upload);
        radius = inflation.findViewById(R.id.radius);
        upload_back=inflation.findViewById(R.id.upload_back);
        choose_exist=inflation.findViewById(R.id.choose_one);
        radiusLbl=inflation.findViewById(R.id.radius_label);
        backLbl=inflation.findViewById(R.id.back_label);
        uploaLbl=inflation.findViewById(R.id.image_upload_lbl);
        next=inflation.findViewById(R.id.next);
        mainPic=inflation.findViewById(R.id.mainPic);
        main= inflation.findViewById(R.id.frameLayout);

        assignLayoutSpecific(inflation);
      //  uploadButton=inflation.findViewById(R.id.upload_click);
    }
    private void setThings_specific_layout(int back_button_st,int back_lbl_st ,int radius_bt_st,int radius_lbl_st ,int choose_exist_st){
        upload_back.setVisibility(back_button_st);       //View.Gone better to radius memory =8 ,
        backLbl.setVisibility(back_lbl_st);
        radiusLbl.setVisibility(radius_lbl_st);
        radius.setVisibility(radius_bt_st);
        choose_exist.setVisibility(choose_exist_st);
    }
    private void assignLayoutSpecific(View inflation) {
        if (getArguments() != null) {
            effect = getArguments().getString(Step1_image.effect_key);
            setUploadButtons();
            switch (effect){
                case effect_cam:{
                    setThings_specific_layout(View.GONE,View.GONE,View.VISIBLE,View.VISIBLE,View.GONE);

                    break;
                }
                case effect_replace:{
                    setThings_specific_layout(View.VISIBLE,View.VISIBLE,View.GONE,View.GONE,View.VISIBLE);
                    setUploadBackButtons();
                    break;
                }
                case effect_remove:{
                    setThings_specific_layout(View.GONE,View.GONE,View.GONE,View.GONE,View.GONE);
                    break;
                }
            }
        }
    }

    private void setNextButton() {
        next.setOnClickListener(view -> {
            if (selected_image_uri==null){
                Toast.makeText(current_context,"Select image !",Toast.LENGTH_LONG).show();
            }
            else if (back_image_uri==null && effect.equals(effect_replace)){
                    Toast.makeText(current_context,"Select Background image !",Toast.LENGTH_LONG).show();

            }else if (radius.getText().toString().isEmpty() && effect.equals(effect_cam)){
                Toast.makeText(current_context,"Set radius !",Toast.LENGTH_LONG).show();

            }
            else{
                AlertDialog dialog = general.prepareSmallAlertDialog(current_context,R.drawable.sandclock,R.string.load,false);
                SendToServer(dialog);

            }

        });
    }




    private void SendToServer(AlertDialog dialog) {
        String selected_uri_string = general.getRealPathFromURI(current_context,selected_image_uri);
        String back_uri_string = (back_image_uri != null && effect.equals(effect_replace))?general.getRealPathFromURI(current_context,back_image_uri):null;//to not give null pointer ,when not replacing ,send the same image
        int  blur_radius_int  =   (radius != null && effect.equals(effect_cam)) ?Integer.parseInt(radius.getText().toString()):0;

        Data inputData = new Data.Builder()
                .putString("effect", effect)
                .putString("selected_image_uri",selected_uri_string)
                .putString("back_image_uri",back_uri_string)
                .putInt("blur_radius",blur_radius_int)
                .putString("Step","Step1_image")
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
                            goToStep2(Objects.requireNonNull(workInfo.getOutputData().getString("imageFilePath")),
                                    Objects.requireNonNull(workInfo.getOutputData().getString("found_ojects"))
                                    ,selected_uri_string,  back_uri_string, blur_radius_int);

                        } else if (workInfo.getState() == WorkInfo.State.FAILED) {
                            Toast.makeText(current_context, "Upload failed!\n"+((workInfo.getOutputData().getString("detail")==null)?"":workInfo.getOutputData().getString("Error")), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void goToStep2(String imageFilePath ,String found_ojects
            ,String selected_uri_string, String back_uri_string, int blur_radius_int) {

        Step2_image fragmentB = new Step2_image();

        Bundle bundle = new Bundle();
        bundle.putString(Step1_image.effect_key, effect);
        bundle.putString("SimageFilePath", imageFilePath);
        bundle.putString("found_ojects", found_ojects);
        bundle.putString("selected_uri_string", selected_uri_string);
        bundle.putString("back_uri_string", back_uri_string);
        bundle.putInt("blur_radius_int", blur_radius_int);

        fragmentB.setArguments(bundle);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(getId(), fragmentB);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setUploadBackButtons() {
        upload_back.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncherBackImage.launch(intent);


        });
        upload_back.setOnLongClickListener(view -> {
            Animation animation = AnimationUtils.loadAnimation(current_context, R.anim.anim1);
            upload_main.startAnimation(animation);
            return false;
        });
    }

    private void setUploadButtons() {
        upload_main .setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryLauncherMainImage.launch(intent);
            // selected_image,back_image


        });
        upload_main.setOnLongClickListener(view -> {
            Animation animation = AnimationUtils.loadAnimation(current_context, R.anim.load);
            upload_main.startAnimation(animation);
            return false;
        });


    }

    private void assign_gallery_launchers() {
        //if error :check premession
        galleryLauncherMainImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            selected_image_uri = data.getData();
                            mainPic.setImageURI(selected_image_uri);
                            Toast.makeText(current_context,"Selected Image :\n"+selected_image_uri,Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
        galleryLauncherBackImage = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            back_image_uri = data.getData();
                            Toast.makeText(current_context,"Selected Image :\n"+back_image_uri,Toast.LENGTH_LONG).show();

                        }
                    }
                }
        );
    }





    private void addAnimationImage() {
        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.anim1);
        animation.setRepeatCount(8);
        //imageToAnimate.startAnimation(animation);
    }
}
