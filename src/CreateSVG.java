
public class CreateSVG {
	private String header = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n"+
							"<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\"\n"+
							"\"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"+
							"<svg xmlns=\"http://www.w3.org/2000/svg\"\n"+
							"xmlns:xlink=\"http://www.w3.org/1999/xlink\" xml:space=\"preserve\"\n";
	
	public CreateSVG(int w, int h) {
		setSVGWidthAndHeight(w, h);
		header += "zoomAndPan=\"disable\" preserveAspectRatio=\"none\" >\n";
		fillSvgData();
		header += "</svg>";
		writeToSVGFile();
	}
	
	private void setSVGWidthAndHeight(int width, int height){
		header += "width=\"" + width + "px\" height=\""+ height + "px\"\n"+
				  "viewBox=\"0 0 " + width + " " + height + "\"\n";		
	}
	
	private void fillSvgData(){
		
	}
	
	private void writeToSVGFile(){
		
	}
}
