package com.example.demo.service;

import com.example.demo.dao.OrderDAO;
import com.example.demo.model.Order;
import javafx.collections.ObservableList;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class PrintService {
    private static PrintService instance;

    private PrintService() {
    }

    public static PrintService getInstance() {
        if (instance == null) {
            instance = new PrintService();
        }
        return instance;
    }

    public boolean printOrder(Stage stage, Node node) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean showDialog = printerJob.showPrintDialog(stage);
            PageLayout layout = printerJob.getPrinter().createPageLayout(Paper.A5, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
            if (showDialog) {
                boolean success = printerJob.printPage(layout, node);
                if (success) {
                    printerJob.endJob();
                    return true;
                } else {
                   return false;
                }
            }
        }
        return false;
    }
}
