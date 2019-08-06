package ru.dobrynin91.Util;

import org.apache.commons.io.IOUtils;
import ru.dobrynin91.entities.ModelCarsXml;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StaxParserClass {

    public static List<ModelCarsXml> getParsedModelCarsXml(String xmlString) {
        List<ModelCarsXml> modelXmlList = new ArrayList<>();
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = factory.createXMLEventReader(IOUtils.toInputStream(xmlString, "UTF-8"));
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    StartElement element = event.asStartElement();
                    @SuppressWarnings("unchecked")
                    Iterator<Attribute> iterator = element.getAttributes();
                    ModelCarsXml modelCarsXml = new ModelCarsXml();
                    while (iterator.hasNext()) {
                        Attribute attribute = iterator.next();
                        QName attributeName = attribute.getName();
                        if ("Make".equalsIgnoreCase(attributeName.getLocalPart())) {
                            if (attribute.getValue() != null) {
                                modelCarsXml.setVendor(attribute.getValue());
                            }
                        }
                        if ("Model".equalsIgnoreCase(attributeName.getLocalPart())) {
                            if (attribute.getValue() != null) {
                                modelCarsXml.setModel(attribute.getValue());
                            }
                        }
                    }
                    modelXmlList.add(modelCarsXml);
                }
            }
            return modelXmlList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
