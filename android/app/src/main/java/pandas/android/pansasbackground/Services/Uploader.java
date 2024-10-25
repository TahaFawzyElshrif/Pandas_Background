package pandas.android.pansasbackground.Services;



import pandas.android.pansasbackground.Helper.Image;
import pandas.android.pansasbackground.fragments.*;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import pandas.android.pansasbackground.Helper.Utils_API;

public class Uploader extends Worker {
    String effect  ,Step ,found_objects;
    int blur_radius ;

    File selected_image,back_image ;

    public static String api="https://taha454-backg.hf.space";
    public static String image_Step1_route=api+"/imageStep1";
    public static String image_Step2_route=api+"/imageStep2";
    public static String video_route=api+"/Video";

    public Uploader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        synchronized (this) {
            try {
                assign_variables();

                Log.d("TAG","Service started");
                //wait(5000);
                Result result_of_upload= call_step();

                Log.d("TAG","Service is Done");
                return result_of_upload;
            } catch (Exception e) {
                Log.e("Error",e.toString());

                return Result.failure();
            }

        }

    }



    private void assign_variables() {
         effect = getInputData().getString("effect");
         blur_radius = getInputData().getInt("blur_radius",33);
         selected_image= new File(Objects.requireNonNull(getInputData().getString("selected_image_uri")));
         back_image =getInputData().getString("back_image_uri")==null?null: new File(getInputData().getString("back_image_uri"));
         Step=getInputData().getString("Step");
        found_objects=getInputData().getString("found_objects");

    }
    private Result call_step() throws Exception{
        Result result_of_api=null;

        switch (Step){
            case "Step1_image":{
                result_of_api=call_step1_api();
                break;
            }
            case "Step2_image":{
                result_of_api=call_step2_api(getApplicationContext());
                break;
            }
            case "Video":{
                result_of_api=Video();
                break;
            }
        }

        return result_of_api;
    }

    private Result call_step2_api(Context context) throws Exception{


        byte[] request;
        switch (effect){
            case Step1_image.effect_cam:{
                request=Utils_API.send_to_image_step_2(
                        context,
                        image_Step2_route,
                        selected_image,
                        back_image,
                        "cam"
                        ,found_objects
                        , ""+blur_radius

                );
                Image.downald_image_file(getApplicationContext(),request);

                break;
            }
            case  Step1_image.effect_remove:{
                request=Utils_API.send_to_image_step_2(
                        context,
                        image_Step2_route,
                        selected_image,
                        back_image,
                        "remove"
                        ,found_objects
                        , "0"

                );
                Image.downald_image_file(getApplicationContext(),request);

                break;
            }
            case Step1_image.effect_replace:{
                request=Utils_API.send_to_image_step_2(
                        context,
                        image_Step2_route,
                        selected_image,
                        back_image,
                        "replace"
                        ,found_objects
                        , "0"

                );
                Image.downald_image_file(getApplicationContext(),request);

            }
        }



        return Result.success();

    }

    private Result Video() {
        return  null;
    }

    private Result call_step1_api() throws Exception{


        String request="";
        switch (effect){
            case Step1_image.effect_cam:{
                request=Utils_API.send_to_image_step_1(
                        image_Step1_route,
                        selected_image,
                        back_image,
                        "cam"
                        , ""+blur_radius

                );
                break;
            }
            case  Step1_image.effect_remove:{
                request=Utils_API.send_to_image_step_1(
                        image_Step1_route,
                        selected_image,
                        back_image,
                        "remove"
                        , "0"

                );
                break;
            }
            case Step1_image.effect_replace:{
                request=Utils_API.send_to_image_step_1(
                        image_Step1_route,
                        selected_image,
                        back_image,
                        "replace"
                        , "0"

                );
            }
        }


        JSONObject jsonResponse = new JSONObject(request);
        String status = jsonResponse.getString("status");
        if (Integer.parseInt(status)==0){
            Data outputData = new Data.Builder()
                    .putInt("status",Integer.parseInt(status))
                    .putString("detail",jsonResponse.getString("detail"))
                    .build();


            return Result.failure(outputData);
        }

        String found_ojects = jsonResponse.getString("message");
        String encodedImage = jsonResponse.getString("image_base64"); // Or extract specific values


        File file = new File(getApplicationContext().getFilesDir(), "image_base64.txt");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(encodedImage.getBytes());
        }

        Data outputData = new Data.Builder()
                .putString("imageFilePath", file.getAbsolutePath())
                .putString("found_ojects",found_ojects)
                .build();


        return Result.success(outputData);



    }


}

