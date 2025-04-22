package org.akrogen.tkui.css.core.resources;

import java.io.InputStream;

public class CSSCoreResources {


	public static InputStream getHTMLSimple() {
		return CSSCoreResources.class.getResourceAsStream("html/simple-html.css");
	}
	
	public static InputStream getHTMLFont() {
		return CSSCoreResources.class.getResourceAsStream("html/font-html.css");
	}
	

	public static InputStream getHTMLMatrix() {
		return CSSCoreResources.class.getResourceAsStream("html/Matrix.css");
	}
	
	public static InputStream getStyleCLass() {
		return CSSCoreResources.class.getResourceAsStream("style-class.css");
	}


}
