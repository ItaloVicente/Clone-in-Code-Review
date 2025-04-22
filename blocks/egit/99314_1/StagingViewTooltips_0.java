package org.eclipse.egit.ui.internal.staging;

import java.util.HashMap;

import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public abstract class FixedJFaceToolTip {
	private Control control;

	private int xShift = 3;

	private int yShift = 0;

	private int popupDelay = 0;

	private int hideDelay = 0;

	private ToolTipOwnerControlListener listener;

	private HashMap<String, Object> data;

	private static Shell CURRENT_TOOLTIP;

	private TooltipHideListener hideListener = new TooltipHideListener();

	private Listener shellListener;

	private boolean hideOnMouseDown = true;

	private boolean respectDisplayBounds = true;

	private boolean respectMonitorBounds = true;

	private int style;

	private Object currentArea;

	public FixedJFaceToolTip(Control control) {
		this(control, ToolTip.RECREATE, false);
	}

	public FixedJFaceToolTip(Control control, int style,
			boolean manualActivation) {
		this.control = control;
		this.style = style;
		this.listener = new ToolTipOwnerControlListener();
		this.shellListener = event -> {
			if (FixedJFaceToolTip.this.control != null
					&& !FixedJFaceToolTip.this.control.isDisposed()) {
				FixedJFaceToolTip.this.control.getDisplay().asyncExec(() -> {
					if (FixedJFaceToolTip.this.control != null
							&& !FixedJFaceToolTip.this.control.isDisposed()
							&& FixedJFaceToolTip.this.control.getDisplay()
									.getActiveShell() != CURRENT_TOOLTIP) {
						toolTipHide(CURRENT_TOOLTIP, event);
					}
				});
			}
		};

		if (!manualActivation) {
			activate();
		}
	}

	public void setData(String key, Object value) {
		if (data == null) {
			data = new HashMap<>();
		}
		data.put(key, value);
	}

	public Object getData(String key) {
		if (data != null) {
			return data.get(key);
		}
		return null;
	}

	public void setShift(Point p) {
		xShift = p.x;
		yShift = p.y;
	}

	public void activate() {
		deactivate();
		control.addListener(SWT.Dispose, listener);
		control.addListener(SWT.MouseHover, listener);
		control.addListener(SWT.MouseMove, listener);
		control.addListener(SWT.MouseExit, listener);
		control.addListener(SWT.MouseDown, listener);
		control.addListener(SWT.MouseWheel, listener);
	}

	public void deactivate() {
		control.removeListener(SWT.Dispose, listener);
		control.removeListener(SWT.MouseHover, listener);
		control.removeListener(SWT.MouseMove, listener);
		control.removeListener(SWT.MouseExit, listener);
		control.removeListener(SWT.MouseDown, listener);
		control.removeListener(SWT.MouseWheel, listener);
	}

	public boolean isRespectDisplayBounds() {
		return respectDisplayBounds;
	}

	public void setRespectDisplayBounds(boolean respectDisplayBounds) {
		this.respectDisplayBounds = respectDisplayBounds;
	}

	public boolean isRespectMonitorBounds() {
		return respectMonitorBounds;
	}

	public void setRespectMonitorBounds(boolean respectMonitorBounds) {
		this.respectMonitorBounds = respectMonitorBounds;
	}

	protected boolean shouldCreateToolTip(Event event) {
		if ((style & ToolTip.NO_RECREATE) != 0) {
			Object tmp = getToolTipArea(event);

			if (tmp == null) {
				hide();
				return false;
			}

			boolean rv = !tmp.equals(currentArea);
			return rv;
		}

		return true;
	}

	private boolean shouldHideToolTip(Event event) {
		if (event != null && event.type == SWT.MouseMove
				&& (style & ToolTip.NO_RECREATE) != 0) {
			Object tmp = getToolTipArea(event);

			if (tmp == null) {
				hide();
				return false;
			}

			boolean rv = !tmp.equals(currentArea);
			return rv;
		}

		return true;
	}

	protected Object getToolTipArea(Event event) {
		return control;
	}

	public void show(Point location) {
		Event event = new Event();
		event.x = location.x;
		event.y = location.y;
		event.widget = control;
		toolTipCreate(event);
	}

	private Shell toolTipCreate(final Event event) {
		if (shouldCreateToolTip(event)) {
			Shell shell = new Shell(control.getShell(),
					SWT.ON_TOP | SWT.TOOL | SWT.NO_FOCUS);
			shell.setLayout(new FillLayout());

			toolTipOpen(shell, event);

			return shell;
		}

		return null;
	}

	private void toolTipShow(Shell tip, Event event) {
		if (!tip.isDisposed()) {
			currentArea = getToolTipArea(event);
			createToolTipContentArea(event, tip);
			if (isHideOnMouseDown()) {
				toolTipHookBothRecursively(tip);
			} else {
				toolTipHookByTypeRecursively(tip, true, SWT.MouseExit);
			}

			tip.pack();
			Point size = tip.getSize();
			Point location = fixupDisplayBounds(size, getLocation(size, event));

			Point cursorLocation = tip.getDisplay().getCursorLocation();

			if (cursorLocation.y == location.y && location.x < cursorLocation.x
					&& location.x + size.x > cursorLocation.x) {
				location.y -= 2;
			}
			tip.setLocation(location);
			tip.setVisible(true);
		}
	}

	private Point fixupDisplayBounds(Point tipSize, Point location) {
		if (respectDisplayBounds || respectMonitorBounds) {
			Rectangle bounds;
			Point rightBounds = new Point(tipSize.x + location.x,
					tipSize.y + location.y);

			Monitor[] ms = control.getDisplay().getMonitors();

			if (respectMonitorBounds && ms.length > 1) {
				bounds = control.getMonitor().getBounds();
				Point p = new Point(location.x, location.y);

				Rectangle tmp;
				for (int i = 0; i < ms.length; i++) {
					tmp = ms[i].getBounds();
					if (tmp.contains(p)) {
						bounds = tmp;
						break;
					}
				}

			} else {
				bounds = control.getDisplay().getBounds();
			}

			if (!(bounds.contains(location) && bounds.contains(rightBounds))) {
				if (rightBounds.x > bounds.x + bounds.width) {
					location.x -= rightBounds.x - (bounds.x + bounds.width);
				}

				if (rightBounds.y > bounds.y + bounds.height) {
					location.y -= rightBounds.y - (bounds.y + bounds.height);
				}

				if (location.x < bounds.x) {
					location.x = bounds.x;
				}

				if (location.y < bounds.y) {
					location.y = bounds.y;
				}
			}
		}

		return location;
	}

	public Point getLocation(Point tipSize, Event event) {
		return control.toDisplay(event.x + xShift, event.y + yShift);
	}

	private void toolTipHide(Shell tip, Event event) {
		if (tip != null && !tip.isDisposed() && shouldHideToolTip(event)) {
			control.getShell().removeListener(SWT.Deactivate, shellListener);
			currentArea = null;
			passOnEvent(tip, event);
			tip.dispose();
			CURRENT_TOOLTIP = null;
			afterHideToolTip(event);
		}
		if (event != null && event.type == SWT.Dispose) {
			deactivate();
			data = null;
		}
	}

	private void passOnEvent(Shell tip, Event event) {
		if (control != null && !control.isDisposed() && event != null
				&& event.widget != control && event.type == SWT.MouseDown) {
			tip.close();
		}
	}

	private void toolTipOpen(final Shell shell, final Event event) {
		if (CURRENT_TOOLTIP != null) {
			toolTipHide(CURRENT_TOOLTIP, null);
		}

		CURRENT_TOOLTIP = shell;

		control.getShell().addListener(SWT.Deactivate, shellListener);

		if (popupDelay > 0) {
			control.getDisplay().timerExec(popupDelay,
					() -> toolTipShow(shell, event));
		} else {
			toolTipShow(CURRENT_TOOLTIP, event);
		}

		if (hideDelay > 0) {
			control.getDisplay().timerExec(popupDelay + hideDelay,
					() -> toolTipHide(shell, null));
		}
	}

	private void toolTipHookByTypeRecursively(Control c, boolean add,
			int type) {
		if (add) {
			c.addListener(type, hideListener);
		} else {
			c.removeListener(type, hideListener);
		}

		if (c instanceof Composite) {
			Control[] children = ((Composite) c).getChildren();
			for (int i = 0; i < children.length; i++) {
				toolTipHookByTypeRecursively(children[i], add, type);
			}
		}
	}

	private void toolTipHookBothRecursively(Control c) {
		c.addListener(SWT.MouseDown, hideListener);
		c.addListener(SWT.MouseExit, hideListener);

		if (c instanceof Composite) {
			Control[] children = ((Composite) c).getChildren();
			for (int i = 0; i < children.length; i++) {
				toolTipHookBothRecursively(children[i]);
			}
		}
	}

	protected abstract Composite createToolTipContentArea(Event event,
			Composite parent);

	protected void afterHideToolTip(Event event) {
	}

	public void setHideDelay(int hideDelay) {
		this.hideDelay = hideDelay;
	}

	public void setPopupDelay(int popupDelay) {
		this.popupDelay = popupDelay;
	}

	public boolean isHideOnMouseDown() {
		return hideOnMouseDown;
	}

	public void setHideOnMouseDown(final boolean hideOnMouseDown) {
		if (CURRENT_TOOLTIP != null && !CURRENT_TOOLTIP.isDisposed()) {
			if (hideOnMouseDown != this.hideOnMouseDown) {
				control.getDisplay().syncExec(() -> {
					if (CURRENT_TOOLTIP != null
							&& CURRENT_TOOLTIP.isDisposed()) {
						toolTipHookByTypeRecursively(CURRENT_TOOLTIP,
								hideOnMouseDown, SWT.MouseDown);
					}
				});
			}
		}

		this.hideOnMouseDown = hideOnMouseDown;
	}

	public void hide() {
		toolTipHide(CURRENT_TOOLTIP, null);
	}

	private class ToolTipOwnerControlListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.Dispose:
			case SWT.KeyDown:
			case SWT.MouseDown:
			case SWT.MouseMove:
			case SWT.MouseWheel:
				toolTipHide(CURRENT_TOOLTIP, event);
				break;
			case SWT.MouseHover:
				toolTipCreate(event);
				break;
			case SWT.MouseExit:
				if (CURRENT_TOOLTIP != null && !CURRENT_TOOLTIP.isDisposed()) {
					if (CURRENT_TOOLTIP.getBounds()
							.contains(control.toDisplay(event.x, event.y))) {
						break;
					}
				}

				toolTipHide(CURRENT_TOOLTIP, event);
				break;
			}
		}
	}

	private class TooltipHideListener implements Listener {

		@Override
		public void handleEvent(Event event) {
			if (event.widget instanceof Control) {

				Control c = (Control) event.widget;
				Shell shell = c.getShell();

				switch (event.type) {
				case SWT.MouseDown:
					if (isHideOnMouseDown()) {
						toolTipHide(shell, event);
					}
					break;
				case SWT.MouseExit:
					Rectangle rect = shell.getBounds();

					if (!rect.contains(c.getDisplay().getCursorLocation())) {
						toolTipHide(shell, event);
					}
					break;
				}
			}
		}
	}
}
