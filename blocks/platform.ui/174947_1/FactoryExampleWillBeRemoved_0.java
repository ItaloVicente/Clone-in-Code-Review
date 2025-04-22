package org.eclipse.jface.resource;

import java.io.InputStream;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

public class Factory {

	public static Image create(Control control, InputStream inputStream) {
		Image image = new Image(control.getDisplay(), inputStream);
		control.addDisposeListener(e -> image.dispose());
		return image;
	}

	public static Font create(Control control, String name, int height, int style) {
		Font font = new Font(control.getDisplay(), name, height, style);
		control.addDisposeListener(e -> font.dispose());
		return font;
	}

}
