package myapplication.com.bluetoothscales.utils;


import java.util.UUID;

/**
 * <br />
 * created by CxiaoX at 2017/1/12 11:48.
 */

public class Constant {

    public static final String SP_KEY_BLE_IS_BIND = "isBind";
    public static final String SP_KEY_BLE_BIND_MAC = "bindMac";
    //设备连接成功广播
    public static final String SUCCESSFUL_DEVICE_CONNECTION = "com.pgt.bjorange.SUCCESSFUL_DEVICE_CONNECTION";
    //断开
    public static final String EQUIPMENT_DISCONNECTED = "com.pgt.bjorange.EQUIPMENT_DISCONNECTED";
    //发送设备数据广播
    public static final String ACTION_BLE_NOTIFY_DATA = "com.pgt.bjorange.ACTION_BLE_NOTIFY_DATA";

    public final static String EXTRA_STATUS = "com.pgt.pedelec.EXTRA_STATUS";
    public static final int USER_GENDER_MALE = 1;
    public static final int USER_GENDER_FEMALE = 0;

    /**
     * 协议服务的UUID
     */
    public final static UUID UUID_SERVICE = UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb7");
    public final static UUID UUID_SERVICE2 = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");

    /**
     * 基本配置,write/read 属性
     */
    public final static UUID UUID_CHARACTERISTIC_CONFIG = UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb9");


    /**
     * 控制功能, write 属性<br />
     */
    public final static UUID UUID_CHARACTERISTIC_CONTROL = UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cba");

    public final static UUID UUID_CHARACTERISTIC_CONTROL2 = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");

    /**
     * 实时数据，notify属性<br />
     */
    public final static UUID UUID_CHARACTERISTIC_NOTIFY_DATA = UUID.fromString("0783b03e-8535-b5a0-7140-a304d2495cb8");

    public final static UUID UUID_CHARACTERISTIC_NOTIFY_DATA2 = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");

}
