package org.eclipse.egit.ui.internal.decorators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationContext;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class DecorationResult implements IDecoration {

	private List<String> prefixes = new ArrayList<String>();

	private List<String> suffixes = new ArrayList<String>();

	private ImageDescriptor overlay = null;

	private Color backgroundColor = null;

	private Font font = null;

	private Color foregroundColor = null;

	public void addOverlay(ImageDescriptor overlayImage) {
		if (overlay == null)
			overlay = overlayImage;
	}

	public void addOverlay(ImageDescriptor overlayImage, int quadrant) {
		addOverlay(overlayImage);
	}

	public void addPrefix(String prefix) {
		prefixes.add(prefix);
	}

	public void addSuffix(String suffix) {
		suffixes.add(suffix);
	}

	public IDecorationContext getDecorationContext() {
		return new DecorationContext();
	}

	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	public void setForegroundColor(Color color) {
		foregroundColor = color;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public ImageDescriptor getOverlay() {
		return overlay;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public Font getFont() {
		return font;
	}

	public String getPrefix() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iter = prefixes.iterator(); iter.hasNext();) {
			sb.append(iter.next());
		}
		return sb.toString();
	}

	public String getSuffix() {
		StringBuilder sb = new StringBuilder();
		for (Iterator<String> iter = suffixes.iterator(); iter.hasNext();) {
			sb.append(iter.next());
		}
		return sb.toString();
	}

}
