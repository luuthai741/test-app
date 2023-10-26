package com.example.demo.service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import com.example.demo.utils.util.ConvertUtil;
import com.example.demo.utils.util.DateUtil;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

import static com.example.demo.utils.util.DateUtil.*;

public class IOService {
    private OrderDAO orderDAO = OrderDAO.getInstance();

    public boolean exportData(ObservableList<Order> orders, String path) throws FileNotFoundException {
        String filename = "Orders_" + convertToString(LocalDate.now(), DD_MM_YYYY_2) + ".csv";
        File file = new File(path + "//" + filename);
        PrintWriter writer = new PrintWriter(file);
        try {
            for (Order order : orders) {
                writer.write(order.getIndex() + "," +
                        order.getLicensePlates() + "," +
                        order.getSeller() + "," +
                        order.getBuyer() + "," +
                        order.getTotalWeight() + "," +
                        order.getVehicleWeight() + "," +
                        order.getCargoWeight() + "," +
                        convertToString(order.getCreatedAt(), DD_MM_YYYY_HH_MM_SS) + "," +
                        convertToString(order.getUpdatedAt(), DD_MM_YYYY_HH_MM_SS) + "," +
                        order.getStatus() + "," +
                        order.getPaymentStatus() + "," +
                        order.getCargoType() + "," +
                        order.getPaymentAmount() + "," +
                        order.getNote() + "," +
                        order.getPayer() + "," +
                        order.getCreatedBy() + "\n"
                );
            }
        }catch (Exception e){
            return false;
        }finally {
            writer.close();
        }
        return true;
    }

    public boolean importData(File file) throws FileNotFoundException {
        if (file == null) {
            return false;
        }
        Scanner scanner = new Scanner(file);
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");
                Order order = new Order();
                order.setIndex(Integer.valueOf(data[0]));
                order.setLicensePlates(data[1]);
                order.setSeller(data[2]);
                order.setBuyer(data[3]);
                order.setTotalWeight(Integer.valueOf(data[4]));
                order.setVehicleWeight(Integer.valueOf(data[5]));
                order.setCargoWeight(Integer.valueOf(data[6]));
                order.setCreatedAt(DateUtil.convertStringToLocalDateTime(data[7], DD_MM_YYYY_HH_MM_SS));
                order.setUpdatedAt(DateUtil.convertStringToLocalDateTime(data[8], DD_MM_YYYY_HH_MM_SS));
                order.setStatus(data[9]);
                order.setPaymentStatus(data[10]);
                order.setCargoType(data[11]);
                order.setPaymentAmount(Double.valueOf(data[12]));
                order.setNote(data[13]);
                order.setPayer(data[14]);
                order.setCreatedBy(data[15]);
                orderDAO.createOrder(order);
            }
        } catch (Exception e) {
            return false;
        } finally {
            scanner.close();
        }
        return true;
    }
}
