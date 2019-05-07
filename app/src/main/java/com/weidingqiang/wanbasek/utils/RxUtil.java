package com.weidingqiang.wanbasek.utils;

import com.weidingqiang.wanbasek.model.http.response.HttpResponse;
import com.weidingqiang.wanbasek.model.http.exception.ApiException;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

    /**
     * 统一线程处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 统一返回结果处理
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<HttpResponse<T>, T> handleTestResult() {   //compose判断结果
        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<HttpResponse<T>> httpResponseFlowable) {
                return httpResponseFlowable.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(HttpResponse<T> httpResponse) {

                        if(httpResponse.getCode() == 0) {
//                            if(EmptyUtils.isEmpty(httpResponse.getData()))
                            if(httpResponse.getData() == null)
                            {
                                 return createData((T)"success");
                            }
                            else {

                            }
                            return createData(httpResponse.getData());
                        } else {

//                            //token 失效   需要重写跳转到登录页面
//                            if(httpResponse.getCode() == 401){
//                                RxBus.getDefault().post(new LoginEvent(LoginEvent.TOKEN_ERROR));
//                                return null;
//                            }
                            return Flowable.error(new ApiException(httpResponse.getMsg(), httpResponse.getCode()));
                        }
                    }
                });
            }
        };
    }
//


    /**
     * 生成Flowable
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

}
