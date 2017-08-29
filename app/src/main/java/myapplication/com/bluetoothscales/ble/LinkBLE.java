package myapplication.com.bluetoothscales.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.util.Log;

import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.utils.ToHex;

import static myapplication.com.bluetoothscales.utils.Constant.UUID_CHARACTERISTIC_CONTROL;
import static myapplication.com.bluetoothscales.utils.Constant.UUID_CHARACTERISTIC_CONTROL2;
import static myapplication.com.bluetoothscales.utils.Constant.UUID_CHARACTERISTIC_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.Constant.UUID_CHARACTERISTIC_NOTIFY_DATA2;
import static myapplication.com.bluetoothscales.utils.Constant.UUID_SERVICE;
import static myapplication.com.bluetoothscales.utils.Constant.UUID_SERVICE2;


/**
 * Created by Administrator on 2016/7/18.
 */
public class LinkBLE implements BluetoothLeClass.OnDisconnectListener {
    private final static String TAG = LinkBLE.class.getSimpleName();
    private BluetoothLeClass mBLE;
    private BluetoothGattCharacteristic notifiChara;
    // private BluetoothGattCharacteristic readChara;
    private BluetoothGattCharacteristic writeChara;
    private boolean isRing = true;
    private boolean isLink = false;
    private String address;

    public String getAddress() {
        return address;
    }

    private BluetoothAdapter mBluetoothAdapter;
    Context context;
    String str;
    Handler handler = new Handler();

    public boolean isLink() {
        return isLink;
    }

    public void setIsLink(boolean isLink) {
        this.isLink = isLink;
    }

    public LinkBLE(final Context context) {
        this.context = context;
        mBLE = new BluetoothLeClass(context);
        if (!mBLE.initialize()) {
            MyApplication.newInstance().clearAllActies();
        }
        mBLE.setOnServiceDiscoverListener(mOnServiceDiscover);
        mBLE.setOnDataAvailableListener(mOnDataAvailable);
        mBLE.setOnDisconnectListener(this);
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
    }

    /**
     * 连接设备
     */
    public boolean LinkBluetooth(String address) {
        this.address = address.toUpperCase();
        Log.e(TAG, address + "连接中...");
        return mBLE.connect(this.address);
    }

    /**
     * 搜索到BLE终端服务的事件
     */
    private BluetoothLeClass.OnServiceDiscoverListener mOnServiceDiscover = new BluetoothLeClass.OnServiceDiscoverListener() {

        @Override
        public void onServiceDiscover(BluetoothGatt gatt) {
            Log.i(TAG, "发现服务" + gatt.getDevice().getAddress());
            if (gatt.getDevice().getName().toLowerCase().contains("nrf51")) {
                BluetoothGattService bleGattService = gatt.getService(UUID_SERVICE2);
                //readChara = bleGattService.getCharacteristic(UUID_CHARACTERISTIC_CONFIG);
                writeChara = bleGattService.getCharacteristic(UUID_CHARACTERISTIC_CONTROL2);
                notifiChara = bleGattService.getCharacteristic(UUID_CHARACTERISTIC_NOTIFY_DATA2);
            } else if (gatt.getDevice().getName().toLowerCase().contains("dialog")) {
                BluetoothGattService bleGattService = gatt.getService(UUID_SERVICE);
                //readChara = bleGattService.getCharacteristic(UUID_CHARACTERISTIC_CONFIG);
                writeChara = bleGattService.getCharacteristic(UUID_CHARACTERISTIC_CONTROL);
                notifiChara = bleGattService.getCharacteristic(UUID_CHARACTERISTIC_NOTIFY_DATA);
            }


            //订阅通知
            mBLE.setCharacteristicNotification(notifiChara, true);

        }

    };

    /**
     * 收到BLE终端数据交互的事件
     */
    private BluetoothLeClass.OnDataAvailableListener mOnDataAvailable = new BluetoothLeClass.OnDataAvailableListener() {

        /**
         * BLE终端数据被读的事件
         */
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.e(TAG, "onCharRead " + gatt.getDevice().getName()
                        + " read "
                        + characteristic.getUuid().toString()
                        + " -> "
                        + ToHex.bytesToHex(characteristic.getValue()));
            }

        }

        /**
         * 收到BLE终端写入数据回调
         */
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt,
                                          BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, "onCharWrite " + gatt.getDevice().getName()
                    + " write "
                    + characteristic.getUuid().toString()
                    + " -> "
                    + ToHex.bytesToHex(characteristic.getValue()));

        }

    };

    //写入数据
    public boolean write(String string) {

        if (writeChara != null) {
            Log.e("write", string);
            writeChara.setValue(ToHex.hexStringToBytes(string));
            return mBLE.writeCharacteristic(writeChara);
        }
        return false;
    }

    //读取数据
    public void read() {
        /*if (readChara != null)
            mBLE.readCharacteristic(readChara);*/
    }

    @Override
    public void onDisconnect(BluetoothGatt gatt) {
        writeChara=null;
        closeBle();
        Log.e(TAG, "设备已断开");


    }

    public boolean initBLE(Context context) {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return false;
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            return false;
        }
        mBluetoothAdapter.enable();
        return true;
    }

    public void closeBle() {
        setIsLink(false);
        isRing = true;
        //mBLE.close();
        mBLE.disconnect();
        writeChara=null;

    }

}
