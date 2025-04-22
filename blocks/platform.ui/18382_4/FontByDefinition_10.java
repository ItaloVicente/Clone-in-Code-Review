package org.eclipse.e4.ui.css.swt.resources;

import org.eclipse.swt.graphics.Color;

public class ColorByDefinition extends VolatileResource<Color> {
	private String symbolicName;
	
	public ColorByDefinition(String symbolicName, Color color) {
		super(color);
		this.symbolicName = symbolicName;
	}
	
	public String getSymbolicName() {
		return symbolicName;
	}
}
