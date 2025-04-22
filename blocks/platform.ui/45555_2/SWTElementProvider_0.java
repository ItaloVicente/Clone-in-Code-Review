package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Region;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

public abstract class AbstractControlSelectionBackgroundCustomizationElement extends ControlElement {

	private final static String SELECTION_BACKGROUND_COLOR = "org.eclipse.e4.ui.css.swt.selectionBackgroundColor"; //$NON-NLS-1$
	private final static String SELECTION_BORDER_COLOR = "org.eclipse.e4.ui.css.swt.selectionBorderColor"; //$NON-NLS-1$
	private final static String HOT_BACKGROUND_COLOR = "org.eclipse.e4.ui.css.swt.hotBackgroundColor"; //$NON-NLS-1$
	private final static String HOT_BORDER_COLOR = "org.eclipse.e4.ui.css.swt.hotBorderColor"; //$NON-NLS-1$

	private static final Listener selectionListener = new Listener() {

		@Override
		public void handleEvent(Event event) {

			Scrollable control = (Scrollable) event.widget;
			int columnCount = 1;
			if (control instanceof Tree) {
				columnCount = ((Tree) control).getColumnCount();

			} else if (control instanceof Table) {
				columnCount = ((Table) control).getColumnCount();
			} else {
				return;
			}

			boolean selected = (event.detail & SWT.SELECTED) != 0;
			boolean hot = (event.detail & SWT.HOT) != 0;

			event.detail &= ~SWT.HOT;

			if (selected || hot) {

				Color foreground = control.getForeground();
				if (foreground == null) {
					return;
				}

				GC gc = event.gc;
				Rectangle area = control.getClientArea();

				boolean handlingOnlyHot = !selected && hot;
				if (handlingOnlyHot) {
					if (columnCount > 1) {
						return;
					}
				}


				String dataBackgroundKey;
				String dataBorderKey;
				if (handlingOnlyHot) {
					dataBackgroundKey = HOT_BACKGROUND_COLOR;
					dataBorderKey = HOT_BORDER_COLOR;
				} else {
					dataBackgroundKey = SELECTION_BACKGROUND_COLOR;
					dataBorderKey = SELECTION_BORDER_COLOR;
				}

				Object dataBackground = control.getData(dataBackgroundKey);
				Object dataBorder = control.getData(dataBorderKey);

				Color background = null;
				if ((dataBackground instanceof Color)) {
					background = (Color) dataBackground;
				}

				Color border = null;
				if ((dataBorder instanceof Color)) {
					border = (Color) dataBorder;
				}

				if (background == null && border == null) {
					return;
				}

				int width = area.width;
				if (event.index == columnCount - 1 || columnCount == 0) {
					if (width > 0) {
						Region region = new Region();
						gc.getClipping(region);
						region.add(event.x, event.y, width, event.height);
						gc.setClipping(region);
						region.dispose();
					}
				}

				if (background != null) {
					Color oldbackground = gc.getBackground();
					gc.setBackground(background);
					try {
						gc.fillRectangle(0, area.y, area.width + 2, area.height);
					} finally {
						gc.setBackground(oldbackground);
					}
				}
				if (border != null) {
					gc.setForeground(border);
					gc.drawRectangle(0, event.y, width - 1, event.height - 1);
				}
				gc.setForeground(foreground);


				event.detail &= ~SWT.BACKGROUND;

				if (control instanceof Table) {
					if ((event.detail & SWT.FOCUSED) != 0) {
						event.detail &= ~SWT.SELECTED;
					} else {
					}
				} else if (control instanceof Tree) {
					event.detail &= ~SWT.SELECTED;
				}

			}
		}
	};

	public AbstractControlSelectionBackgroundCustomizationElement(Control control, CSSEngine engine) {
		super(control, engine);
	}

	private void setSelectionListener(Control control) {
		control.removeListener(SWT.EraseItem, selectionListener);
		control.addListener(SWT.EraseItem, selectionListener);
	}

	@Override
	public Control getControl() {
		return super.getControl();
	}

	public void setSelectionBackgroundColor(Color color) {
		Control control = getControl();
		control.setData(SELECTION_BACKGROUND_COLOR, color);
		setSelectionListener(control);
	}


	public Color getSelectionBackgroundColor() {
		Control control = getControl();
		return getSelectionBackgroundColor(control);
	}

	public static Color getSelectionBackgroundColor(Control control) {
		Object data = control.getData(SELECTION_BACKGROUND_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}

	public void setSelectionBorderColor(Color color) {
		Control control = getControl();
		control.setData(SELECTION_BORDER_COLOR, color);
		setSelectionListener(control);
	}

	public Color getSelectionBorderColor() {
		Control control = getControl();
		return getSelectionBorderColor(control);
	}

	public static Color getSelectionBorderColor(Control control) {
		Object data = control.getData(SELECTION_BORDER_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}

	public void setHotBackgroundColor(Color color) {
		Control control = getControl();
		control.setData(HOT_BACKGROUND_COLOR, color);
		setSelectionListener(control);
	}

	public Color getHotBackgroundColor() {
		Control control = getControl();
		return getHotBackgroundColor(control);
	}

	public static Color getHotBackgroundColor(Control control) {
		Object data = control.getData(HOT_BACKGROUND_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}

	public void setHotBorderColor(Color color) {
		Control control = getControl();
		control.setData(HOT_BORDER_COLOR, color);
		setSelectionListener(control);
	}

	public Color getHotBorderColor() {
		Control control = getControl();
		return getHotBorderColor(control);
	}

	public static Color getHotBorderColor(Control control) {
		Object data = control.getData(HOT_BORDER_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}

}
