package com.getjavajob.schekovskiy.list2.task1.dao;

import com.getjavajob.schekovskiy.list2.task1.model.Customer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface CustomerDao {

    BigDecimal getSumAllOrdersPrice();

    Long getCustomerIdWithMaxSumOrdersPrice();

    BigDecimal getBiggestOrderSum();

    BigDecimal getSmallestOrderSum();

    Long getOrdersAmount();

    void setCustomersFromXml(String xml, InputStream xsd) throws ParserConfigurationException, SAXException, IOException;

    BigDecimal getAverageOrderSumPrice();

    Set<Customer> getCustomersWithOrdersSumMoreThanN(BigDecimal n);

    List<Customer> getCustomers();
}
