package org.eclipse.ui.gtk;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.swt.dom.WidgetElement;
import org.eclipse.swt.internal.gtk.OS;
import org.eclipse.swt.widgets.Widget;

@SuppressWarnings("restriction")
public class DarkThemeElement extends WidgetElement {

	public DarkThemeElement(Widget widget, CSSEngine engine) {
		super(widget, engine);
	}

	@Override
	public void reset() {
		super.reset();

		OS.gdk_flush();
		OS.g_object_set(OS.gtk_settings_get_default(), "gtk-application-prefer-dark-theme".getBytes(), false, //$NON-NLS-1$
				0);
		OS.g_object_notify(OS.gtk_settings_get_default(), "gtk-application-prefer-dark-theme".getBytes());
	}

}
