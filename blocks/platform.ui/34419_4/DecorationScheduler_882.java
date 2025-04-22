package org.eclipse.ui.internal.decorators;

import java.util.List;
import java.util.ListIterator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class DecorationResult {

	private List prefixes;

	private List suffixes;

	private ImageDescriptor[] descriptors;

	private Color foregroundColor;

	private Color backgroundColor;

	private Font font;

	DecorationResult(List prefixList, List suffixList,
			ImageDescriptor[] imageDescriptors, Color resultForegroundColor,
			Color resultBackgroundColor, Font resultFont) {
		prefixes = prefixList;
		suffixes = suffixList;

		if (hasOverlays(imageDescriptors)) {
			descriptors = imageDescriptors;
		}
		foregroundColor = resultForegroundColor;
		backgroundColor = resultBackgroundColor;
		font = resultFont;
	}

	private boolean hasOverlays(ImageDescriptor[] imageDescriptors) {
		for (int i = 0; i < imageDescriptors.length; i++) {
			if (imageDescriptors[i] != null) {
				return true;
			}
		}
		return false;
	}

	Image decorateWithOverlays(Image image, ResourceManager manager) {

		if (image == null || descriptors == null) {
			return image;
		}
		
		Rectangle bounds = image.getBounds();
		Point size = new Point(bounds.width, bounds.height);
		DecorationOverlayIcon icon = new DecorationOverlayIcon(image, descriptors, size);
		return manager.createImage(icon);
	}

	public String decorateWithText(String text) {

		if (prefixes.isEmpty() && suffixes.isEmpty()) {
			return text;
		}

		StringBuffer result = new StringBuffer();

		ListIterator prefixIterator = prefixes.listIterator();

		while (prefixIterator.hasNext()) {
			result.append(prefixIterator.next());
		}

		result.append(text);

		ListIterator suffixIterator = suffixes.listIterator();

		while (suffixIterator.hasNext()) {
			result.append(suffixIterator.next());
		}

		return result.toString();
	}

	ImageDescriptor[] getDescriptors() {
		return descriptors;
	}

	List getPrefixes() {
		return prefixes;
	}

	List getSuffixes() {
		return suffixes;
	}

	Color getBackgroundColor() {
		return backgroundColor;
	}

	Font getFont() {
		return font;
	}

	Color getForegroundColor() {
		return foregroundColor;
	}
}
