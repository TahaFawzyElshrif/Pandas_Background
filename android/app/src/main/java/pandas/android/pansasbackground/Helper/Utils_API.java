package pandas.android.pansasbackground.Helper;

import android.content.Context;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.File;

public class Utils_API {

    public static String send_to_image_step_1(String requestURL,
                                              File imageFile,
                                              File backgroundImage,
                                              String typeOfFilters,
                                              String blurRadius) throws Exception {

        // Create HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create POST request
            HttpPost uploadFile = new HttpPost(requestURL);

            // Build the MultipartEntity
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("image_file", imageFile, ContentType.DEFAULT_BINARY, imageFile.getName());

            if (backgroundImage != null) {
                builder.addBinaryBody("background_image", backgroundImage, ContentType.DEFAULT_BINARY, backgroundImage.getName());
            }

            // Add form fields
            builder.addTextBody("type_of_filters", typeOfFilters, ContentType.TEXT_PLAIN);
            builder.addTextBody("blur_radius", blurRadius, ContentType.TEXT_PLAIN);

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);

           // String clientIpAddress="192.168.1.5";
           // uploadFile.setHeader("X-Forwarded-For", clientIpAddress);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                // Get response entity
                HttpEntity responseEntity = response.getEntity();
                // Convert the entity to a String
                return EntityUtils.toString(responseEntity); // Get response as String
            }
        }
    }

    public static byte[] send_to_image_step_2(Context context,String requestURL,
                                              File imageFile,
                                              File backgroundImage,
                                              String typeOfFilters,
                                              String things_replace,
                                              String blurRadius) throws Exception {

        // Create HttpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create POST request
            HttpPost uploadFile = new HttpPost(requestURL);

            // Build the MultipartEntity
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("image_file", imageFile, ContentType.DEFAULT_BINARY, imageFile.getName());

            if (backgroundImage != null) {
                builder.addBinaryBody("background_image", backgroundImage, ContentType.DEFAULT_BINARY, backgroundImage.getName());
            }

            // Add form fields
            builder.addTextBody("type_of_filters", typeOfFilters, ContentType.TEXT_PLAIN);
            builder.addTextBody("blur_radius", blurRadius, ContentType.TEXT_PLAIN);
            builder.addTextBody("things_replace", things_replace, ContentType.TEXT_PLAIN);


            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);

            // Execute the request
            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                // Get response entity
                HttpEntity responseEntity = response.getEntity();

                //downloadFile(context,response);
                // Convert the entity to a String
                return EntityUtils.toByteArray(responseEntity);
            }
        }
    }


}
