package com.example.demo.data;

import com.example.demo.dao.SettingDAO;
import com.example.demo.listener.WeighingScaleListener;
import com.example.demo.model.Setting;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import static com.example.demo.utils.constants.SettingKey.COM_PORT;

public class WeighingScale {
    private SettingDAO settingDAO = SettingDAO.getInstance();

    private SerialPort serialPort;

    public WeighingScale() {
        Setting setting = settingDAO.getByKey(COM_PORT);
        if (setting == null) {
            throw new RuntimeException("Chưa cấu hình cổng COM");
        }
        boolean isHasPort = false;
        for (SerialPort port : SerialPort.getCommPorts()) {
            if (port.getSystemPortName().equalsIgnoreCase(setting.getValue())){
             isHasPort = true;
            }
        }
        if (!isHasPort){
            throw new RuntimeException("Không tìm thấy cổng COM");
        }
        serialPort = SerialPort.getCommPort(setting.getValue());
    }

    public void connect() {
        try {
            serialPort.openPort();
            serialPort.setComPortParameters(9600, 8, 1, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void disconnect() {
        serialPort.closePort();
    }

    public void addListener(WeighingScaleListener listener) {
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return 0;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                System.out.println(new String(event.getReceivedData()));
//                listener.onWeightChanged(Double.parseDouble(event.getReceivedData()));
            }
        });
    }
}
