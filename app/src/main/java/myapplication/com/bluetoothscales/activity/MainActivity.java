package myapplication.com.bluetoothscales.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.RadioGroup;

import com.kitnew.ble.QNApiManager;
import com.kitnew.ble.QNBleApi;
import com.kitnew.ble.QNBleCallback;
import com.kitnew.ble.QNBleDevice;
import com.kitnew.ble.QNBleScanCallback;
import com.kitnew.ble.QNData;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.util.List;

import butterknife.BindView;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseActivity;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.fragment.DiscoverFragment;
import myapplication.com.bluetoothscales.fragment.HomeFragment;
import myapplication.com.bluetoothscales.fragment.TrendFragment;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.DateUtil.LONG_DATE_FORMAT;
import static myapplication.com.bluetoothscales.utils.DateUtil.stringtoDate;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private final static int REQUECT_CODE_COARSE = 1;

    @BindView(R.id.main_rgrpNavigation)
    RadioGroup mainRgrpNavigation;
    private Fragment[] frags = new Fragment[3];
    protected BaseFragment currentFragment;
    private HomeFragment homeFragment;
    QNBleApi qnBleApi;
    QNBleDevice device;
    private BluetoothAdapter mBluetoothAdapter;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        qnBleApi = QNApiManager.getApi(this);
        toastor = new Toastor(this);
        initBLE();
        initPermission();
        initData();

        mainRgrpNavigation.setOnCheckedChangeListener(this);
        mainRgrpNavigation.check(R.id.main_home);


    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                showFragment(0);
                break;
            case R.id.main_trend:
                showFragment(1);
                break;
            case R.id.main_discover:
                showFragment(2);
                break;

        }
    }

    private void initData() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (!homeFragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, homeFragment).commit();

            currentFragment = homeFragment;
        }

    }

    private void showFragment(int position) {
        if (frags[position] == null) {
            frags[position] = getFrag(position);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), frags[position]);
    }

    @Nullable
    private Fragment getFrag(int index) {
        switch (index) {
            case 0:
                return new HomeFragment();
            case 1:
                return new TrendFragment();
            case 2:
                return new DiscoverFragment();

            default:
                return null;
        }
    }


    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.main_frame, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = (BaseFragment) fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initPermission() {
        MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_COARSE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
       // mBluetoothAdapter.enable();
    }

    @PermissionGrant(REQUECT_CODE_COARSE)
    public void requestSdcardSuccess() {
        qnBleApi.startLeScan(null, null, new QNBleScanCallback() {
            //如果失败，会在这个方法中返回错误码
            public void onCompete(int errorCode) {
            }

            //如果扫描到设备，会在这个方法返回这个设备的相关信息
            public void onScan(QNBleDevice bleDevice) {
                device = bleDevice;
                qnBleApi.stopScan();
                qnBleApi.connectDevice(device, "362927657", 176, 1, stringtoDate("1991-06-24", LONG_DATE_FORMAT), qnBleCallback);
            }
        });
    }

    @PermissionDenied(REQUECT_CODE_COARSE)
    public void requestSdcardFailed() {
        MPermissions.requestPermissions(MainActivity.this, REQUECT_CODE_COARSE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    QNBleCallback qnBleCallback = new QNBleCallback() {
        /**
         * 开始连接 在主线程中回调
         *
         * @param qnBleDevice 轻牛蓝牙设备
         */
        @Override
        public void onConnectStart(QNBleDevice qnBleDevice) {
            Log.e("Main", "开始链接");
        }

        /**
         * 已经连接上了 在主线程中回调
         *
         * @param qnBleDevice 轻牛蓝牙设备
         */
        @Override
        public void onConnected(QNBleDevice qnBleDevice) {
            Log.e("Main", "连接成功");
        }

        /**
         * 断开了蓝牙连接 在主线程中回调
         *
         * @param qnBleDevice 轻牛蓝牙设备
         */
        @Override
        public void onDisconnected(QNBleDevice qnBleDevice) {
            if (!mBluetoothAdapter.isEnabled())
                mBluetoothAdapter.enable();
            qnBleApi.startLeScan(null, null, new QNBleScanCallback() {
                //如果失败，会在这个方法中返回错误码
                public void onCompete(int errorCode) {
                }

                //如果扫描到设备，会在这个方法返回这个设备的相关信息
                public void onScan(QNBleDevice bleDevice) {
                    device = bleDevice;
                    qnBleApi.stopScan();
                    qnBleApi.connectDevice(device, "362927657", 176, 1, stringtoDate("1991-06-24", LONG_DATE_FORMAT), qnBleCallback);
                }
            });
            Log.e("Main", "断开连接");
        }

        /**
         * 收到了不稳定的体重数据，在称重前期会不断被调用 在主线程中回调
         *
         * @param qnBleDevice 轻牛蓝牙设备
         * @param weight    不稳定的体重
         */
        @Override
        public void onUnsteadyWeight(QNBleDevice qnBleDevice, float weight) {
            Log.e("Main", "不稳定的体重" + weight);

        }

        /**
         * 收到了稳定的测量数据 在主线程中回调
         *
         * @param qnBleDevice 轻牛蓝牙设备
         * @param qnData      轻牛测量数据
         */
        @Override
        public void onReceivedData(QNBleDevice qnBleDevice, QNData qnData) {
            MyApplication.newInstance().setQnData(qnData);
            Log.e("Main", "轻牛测量数据" + qnData.getWeight());
            Intent intent = new Intent();
            intent.setAction(ACTION_BLE_NOTIFY_DATA);
            sendBroadcast(intent);
        }

        /**
         * 收到了存储数据 在主线程中回调
         *
         * @param qnBleDevice 轻牛蓝牙设备
         * @param list     存储数据数组（包含多个），可用{@link QNData#getUserId()}判断是哪个用户的数据
         */
        @Override
        public void onReceivedStoreData(QNBleDevice qnBleDevice, List<QNData> list) {
            Log.e("Main", "收到了存储数据" + list.size());

        }

        @Override
        public void onDeviceModelUpdate(QNBleDevice qnBleDevice) {
            Log.e("Main", "onDeviceModelUpdate" + qnBleDevice.getDeviceName());
        }

        /**
         * 连接中 在主线程中回调
         *
         * @param i 轻牛蓝牙设备
         */
        @Override
        public void onCompete(int i) {
            Log.e("Main", "onCompete" + i);
        }
    };

    private void initBLE() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            toastor.showSingletonToast("手机蓝牙异常");
           // finish();
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            toastor.showSingletonToast("手机蓝牙异常");
            return;
        }
        mBluetoothAdapter.enable();
    }
}
