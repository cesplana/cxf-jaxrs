package com.cne.rest.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.cne.rest.constants.CommonConstants;

/**
 * 
 * @author msiadmin
 *
 */
public class PDFGenerator {

	public PDFGenerator() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param document
	 *            an instance of {@link Document}
	 * @return
	 * @throws URISyntaxException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public byte[] converToPDF(Document document) {
		//OutputStream os = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			
			//StreamSource xmlSource = new StreamSource(document.toString());

			DOMSource xmlSource = new DOMSource(document);
			FopFactory fopFactory = FopFactory.newInstance(new File(getClass().getResource(CommonConstants.FOP_CONFIG).toURI()));

			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			
			//os = new FileOutputStream(new File("D:/pdf/print-overview.pdf"));
			outStream = new ByteArrayOutputStream();
			
			//Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outStream);
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outStream);
			
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(
					new StreamSource(new File(getClass().getResource(CommonConstants.REPORT_TEMPLATE).toURI())));

			Result result = new SAXResult(fop.getDefaultHandler());

			transformer.transform(xmlSource, result);

		} catch (URISyntaxException | TransformerException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				//if (os != null) os.close();
				if (outStream != null) outStream.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return outStream.toByteArray();
	}
}
