package org.akrogen.tkui.css.swt.resources;

import java.io.InputStream;

public class CSSSWTResources {


	public static InputStream getSWT() {
		return CSSSWTResources.class.getResourceAsStream("swt/swt.css");
	}

	public static InputStream getSWTMatrix() {
		return CSSSWTResources.class.getResourceAsStream("swt/Matrix.css");
	}

	public static InputStream getSWTVista() {
		return CSSSWTResources.class.getResourceAsStream("swt/Vista.css");
	}

	public static InputStream getSWTOsx() {
		return CSSSWTResources.class.getResourceAsStream("swt/Osx.css");
	}

	public static InputStream getSWTPseudoCLass() {
		return CSSSWTResources.class
				.getResourceAsStream("swt/pseudo-class.css");
	}
}
