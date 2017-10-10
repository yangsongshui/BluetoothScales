package myapplication.com.bluetoothscales.app;

import android.app.Activity;
import android.util.Log;

import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNData;
import com.kitnew.ble.QNResultCallback;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.bluetoothscales.utils.SpUtils;

/**
 * Created by omni20170501 on 2017/6/8.
 */

public class MyApplication extends LitePalApplication {
    private static final String TAG = "MyApplication";

    private static MyApplication instance;
    public static List<Activity> activitiesList = new ArrayList<Activity>(); // 活动管理集合
    private QNData qnData;
    public boolean isLink = false;
    public boolean isMeasure = false;

    /**
     * 获取单例
     *
     * @return MyApplication
     */
    public static MyApplication newInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        LitePal.initialize(this);
        SpUtils.init(this);
        QNApiManager.getApi(getApplicationContext()).initSDK("123456789", false, new QNResultCallback() {
            @Override
            public void onCompete(int errorCode) {
                //执行结果，为0则成功，其它则参考api文档的种的错误码
                Log.e("MyApplication",errorCode+"");
            }
        });


    }

    /**
     * 把活动添加到活动管理集合
     *
     * @param activity
     */
    public void addActyToList(Activity activity) {
        if (!activitiesList.contains(activity))
            activitiesList.add(activity);
    }

    /**
     * 把活动从活动管理集合移除
     *
     * @param activity
     */
    public void removeActyFromList(Activity activity) {
        if (activitiesList.contains(activity))
            activitiesList.remove(activity);
    }

    /**
     * 程序退出
     */
    public void clearAllActies() {
        for (Activity acty : activitiesList) {
            if (acty != null)
                acty.finish();
        }

    }

    public QNData getQnData() {
        return qnData;
    }

    public void setQnData(QNData qnData) {
        this.qnData = qnData;
    }
}
