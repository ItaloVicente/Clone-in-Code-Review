package org.eclipse.e4.ui.internal.css.swt;

import org.eclipse.swt.graphics.Color;

public interface IHeapStatus {

	Color getUsedMemColor();

	void setUsedMemColor(Color color);

	Color getLowMemColor();

	void setLowMemColor(Color color);

	Color getFreeMemColor();

	void setFreeMemColor(Color color);

}
