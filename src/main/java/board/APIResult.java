package board;

import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;

public class APIResult {
    public static void main(String[] args) {
    }

    public static ResultDto printResult() {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
//        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("http://openapi.seoul.go.kr:8088/56494b554e7075703130344566534956/json/TbPublicWifiInfo/1/1000/")
                .get()
                .build();

        // 있는 만큼 보여주게 해야됨
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            assert response.body() != null;
            return gson.fromJson(response.body().string(), ResultDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}