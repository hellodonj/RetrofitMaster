package com.djj.retrofitmaster;


import com.djj.retrofitmaster.baseHttp.MyObserver;
import com.djj.retrofitmaster.baseHttp.RetrofitUtils;
import com.djj.retrofitmaster.baseHttp.RxHelper;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * desc:请求的封装
 * author:djj
 * date:2019/12/10 18:12
 */
public class RequestUtils {

    /**
     *
     * @param context
     * @param demoBeanVo
     */
    public static void getTimetable(RxAppCompatActivity context, MyObserver<DemoBeanVo> demoBeanVo) {
        RetrofitUtils.getApiServer().getTimetable()
                .compose(RxHelper.observableIO2Main(context))
                .subscribe(demoBeanVo);

    }

//    /**
//     * Post 请求demo
//     *
//     * @param context
//     * @param consumer
//     */
//    public static void postDemo(RxAppCompatActivity context, String name, String password, Observer<Demo> consumer) {
//        RetrofitUtils.getApiUrl()
//                .postUser(name, password).compose(RxHelper.observableIO2Main(context))
//                .subscribe(consumer);
//    }
//
//    /**
//     * Put 请求demo
//     *
//     * @param context
//     * @param consumer
//     */
//    public static void putDemo(RxFragment context, String access_token, Observer<Demo> consumer) {
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("Accept", "application/json");
//        headers.put("Authorization", access_token);
//        RetrofitUtils.getApiUrl()
//                .put(headers, "厦门").compose(RxHelper.observableIO2Main(context))
//                .subscribe(consumer);
//    }
//
//    /**
//     * Delete 请求demo
//     *
//     * @param context
//     * @param consumer
//     */
//    public static void deleteDemo(RxFragment context, String access_token, Observer<Demo> consumer) {
//        RetrofitUtils.getApiUrl()
//                .delete(access_token, 1).compose(RxHelper.observableIO2Main(context))
//                .subscribe(consumer);
//    }
//
//    /**
//     * 上传图片
//     *
//     * @param context
//     * @param observer
//     */
//    public static void upImagView(RxFragment context, String access_token, String str, Observer<Demo> observer) {
//        File file = new File(str);
////        File file = new File(imgPath);
//        Map<String, String> header = new HashMap<String, String>();
//        header.put("Accept", "application/json");
//        header.put("Authorization", access_token);
////        File file =new File(filePath);
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
////        RequestBody requestFile =
////                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body =
//                MultipartBody.Part.createFormData("file", file.getName(), reqFile);
//        RetrofitUtils.getApiUrl().uploadImage(header, body).compose(RxHelper.observableIO2Main(context))
//                .subscribe(observer);
//    }
//
//    /**
//     * 上传多张图片
//     *
//     * @param files
//     */
//    public static void upLoadImg(RxFragment context, String access_token, List<File> files, Observer<Demo> observer1) {
//        Map<String, String> header = new HashMap<String, String>();

//        header.put("Accept", "application/json");
//        header.put("Authorization", access_token);
//        MultipartBody.Builder builder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);//表单类型
//        for (int i = 0; i < files.size(); i++) {
//            File file = files.get(i);
//            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
//            builder.addFormDataPart("file", file.getName(), photoRequestBody);
//        }
//        List<MultipartBody.Part> parts = builder.build().parts();
//        RetrofitUtils.getApiUrl().uploadImage1(header, parts).compose(RxHelper.observableIO2Main(context))
//                .subscribe(observer1);
//    }
}
