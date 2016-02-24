/**
 * Based on an example found here: https://xmlgraphics.apache.org/batik/using/svg-generator.html
 */
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class CreateSVG {
	DOMImplementation domImp;
	String svgNS;
	SVGGraphics2D svgGen;
	Document doc;
	GraphicsWrapper gw;

	public CreateSVG(GraphicsWrapper gw) {
		this.gw = gw;
		domImp = GenericDOMImplementation.getDOMImplementation();
		svgNS = "http://www.w3.org/2000/svg";
		doc = domImp.createDocument(svgNS, "svg", null);
		svgGen = new SVGGraphics2D(doc);
		svgGen.setSVGCanvasSize(new Dimension(Constant.INITIAL_WINDOW_WIDTH, Constant.INITIAL_WINDOW_HEIGHT));
	}

	public SVGGraphics2D getSVGGenerator() {
		return svgGen;
	}

	public void writeToSVGFile(ArrayList<Stroke> strokes) {
		try {
			PrintWriter pw = new PrintWriter("my-drawing.svg", "UTF-8");

			for (Stroke s : strokes) {
				int size = s.getPoints().size();
				int[] xList = new int[size];
				int[] yList = new int[size];
				int i = 0;
				for (Point2D p : s.getPoints()) {
					p = gw.convertWorldSpaceUnitsToPixels(p);
					xList[i] = (int) p.x();
					yList[i] = (int) p.y();
					i++;
				}
				svgGen.setColor(new Color(s.color_red, s.color_green, s.color_blue));
				svgGen.drawPolyline(xList, yList, size);
			}
			svgGen.stream(pw, true);

		} catch (SVGGraphics2DIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendSVG() {
		String to = "chrisbama92@gmail.com,philippejrm@gmail.com,pockoface@gmail.com";
		String from = "no-reply@blackandyellow.com";

		final String username = "palyfight";
		final String password = "czZqcWExd2pwZWJj";

		String server = "mail.smtp2go.com";
		String port = "2525";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", server);
		props.put("mail.smtp.port", port);

		// Get the Session object.
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject("Image format SVG pour le lab3 de GTI745");
			
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText("L'image est en piece jointe");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			
			messageBodyPart = new MimeBodyPart();
			String filename = "my-drawing.svg";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			message.setContent(multipart);
			Transport.send(message);
			System.out.println("Email successfully sent ... :)");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
