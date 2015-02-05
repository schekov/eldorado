package com.getjavajob.schekovskiy.list2.task1.controller;

import com.getjavajob.schekovskiy.list2.task1.dao.CustomerDao;
import com.getjavajob.schekovskiy.list2.task1.dao.CustomerDaoImpl;
import com.getjavajob.schekovskiy.list2.task1.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;


@org.springframework.stereotype.Controller
public class Controller {

    CustomerDao customerDao;
    Set<Customer> list;

    private
    @Autowired
    ServletContext servletContext;

    public Controller() {
    }

    @Autowired
    public Controller(CustomerDao customerDao) throws ParserConfigurationException, SAXException, IOException {
        this.customerDao = new CustomerDaoImpl();
    }


    @RequestMapping(value = "/uploadXml", method = RequestMethod.GET)
    public String getUploadXml() {
        return "uploadXml";
    }

    @RequestMapping(value = "/uploadXml", method = RequestMethod.POST)
    public String uploadxml(@RequestParam(value = "file", required = false) MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File tempf = new File("tempf.xml");
                File.createTempFile("tempf", ".xml");
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(tempf));
                stream.write(bytes);
                stream.close();
                InputStream xsd = servletContext.getResourceAsStream("/WEB-INF/inputForm.xsd");
                customerDao.setCustomersFromXml(tempf.getName(), xsd);
                return "redirect:webFormPage";
            } catch (Exception e) {
                return "You failed to upload " + " => " + e.getMessage();
            }
        } else
            return "uploadXml";
    }

    @RequestMapping(value = "/webFormPage", method = RequestMethod.GET)
    public String initWebFormPage(Map<String, Object> model) throws IOException, SAXException, ParserConfigurationException {
        model.put("customers", customerDao.getCustomers());
        BigDecimal sumAllOrdersPrice = customerDao.getSumAllOrdersPrice();
        BigDecimal biggestOrderSum = customerDao.getBiggestOrderSum();
        BigDecimal smallestOrderSum = customerDao.getSmallestOrderSum();
        Long ordersAmount = customerDao.getOrdersAmount();
        BigDecimal averageOrderSumPrice = customerDao.getAverageOrderSumPrice();

        model.put("sumAllOrdersPrice", sumAllOrdersPrice);
        model.put("biggestOrderSum", biggestOrderSum);
        model.put("smallestOrderSum", smallestOrderSum);
        model.put("ordersAmount", ordersAmount);
        model.put("averageOrderSumPrice", averageOrderSumPrice);
        model.put("filtratedCustomers", list);
        return "webFormPage";
    }

    @RequestMapping(value = "/webFormPage", method = RequestMethod.POST)
    public String getCustomersWithN(@RequestParam(required = false) BigDecimal n, Map<String, Object> model) throws IOException, SAXException, ParserConfigurationException {
        list = customerDao.getCustomersWithOrdersSumMoreThanN(n);
        return "redirect:webFormPage";
    }
}
