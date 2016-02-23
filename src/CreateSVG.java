/**
 * Based on an example found here: https://xmlgraphics.apache.org/batik/using/svg-generator.html
 */
import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;

import java.awt.Color;
import java.awt.Point;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

import org.apache.batik.dom.GenericDOMImplementation;


public class CreateSVG {
	DOMImplementation domImp;
	String svgNS;
	SVGGraphics2D svgGen;
	Document doc;
	
	public CreateSVG(){
		domImp = GenericDOMImplementation.getDOMImplementation();
		svgNS = "http://www.w3.org/2000/svg";
		doc = domImp.createDocument(svgNS, "svg", null);
		svgGen = new SVGGraphics2D(doc);
	}
	
	public SVGGraphics2D getSVGGenerator(){
		return svgGen;
	}
	
	public void writeToSVGFile(ArrayList<Stroke > strokes){
		try {
			Writer out = new OutputStreamWriter(System.out, "UTF-8");
			
			for(Stroke s: strokes){
				int size = s.getPoints().size();
				int[] xList = new int[size];
				int[] yList = new int[size];
				int i = 0;
				for(Point2D p: s.getPoints()){
					xList[i] = (int)p.x();
					yList[i] = (int)p.y();
					i++;
				}
				svgGen.setColor(new Color(s.color_red,s.color_green,s.color_blue));
				svgGen.drawPolyline(xList, yList, size);
			}
			svgGen.stream(out, true);

			
		} catch (SVGGraphics2DIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
