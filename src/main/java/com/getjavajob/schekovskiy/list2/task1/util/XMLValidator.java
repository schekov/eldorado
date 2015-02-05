package com.getjavajob.schekovskiy.list2.task1.util;


import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class XMLValidator {


    public static boolean validateXMLSchema(String xml, InputStream xsd) {

        try {
            SchemaFactory factory =
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            File outf = new File("schema.xsd");
            OutputStream outputStream = new FileOutputStream(outf);
            File.createTempFile("schema", ".xsd");

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = xsd.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            Schema schema = factory.newSchema(outf);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

}
