package org.eclipse.e4.ui.css.swt.resources;

import org.eclipse.swt.graphics.Font;

public class FontByDefinition extends VolatileResource<Font> {	
	private String symbolicName;
	
	private Integer height;
	
	private Integer style;	
	
	public FontByDefinition(String symbolicName, Font font) {
		super(font);
		this.symbolicName = symbolicName;
	}
	
	public String getSymbolicName() {
		return symbolicName;
	}
	
	public Integer getHeight() {
		return height;
	}	 
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Integer getStyle() {
		return style;
	}
	
	public void setStyle(Integer style) {
		this.style = style;
	}
}
