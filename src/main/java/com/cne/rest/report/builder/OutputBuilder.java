package com.cne.rest.report.builder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import com.cne.rest.cxf.model.ReportDocumentModel;
import com.cne.rest.report.PDFGenerator;

public class OutputBuilder {

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static byte[] buildReport(ReportDocumentModel model) {
		Document document = buildDocument(model);
		return new PDFGenerator().converToPDF(document);
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	private static Document buildDocument(ReportDocumentModel model) {
		
		Document document  = null;
		try {
			JAXBContext context = JAXBContext.newInstance(ReportDocumentModel.class);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			document = db.newDocument();
			
			Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(model, document);
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
			
		} catch (JAXBException | ParserConfigurationException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return document;
	}
}
