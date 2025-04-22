
package org.eclipse.ui.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public abstract class QuickMenuCreator {

	private static final int CHAR_INDENT = 3;

	public void createMenu() {
		final Display display = Display.getCurrent();
		if (display == null) {
			return;
		}
		Control focus = display.getFocusControl();
		if (focus == null || focus.isDisposed()) {
			return;
		}

		MenuManager menu = new MenuManager();
		fillMenu(menu);
		final Menu quickMenu = menu.createContextMenu(focus.getShell());
		Point location = computeMenuLocation(focus);
		if (location == null) {
			return;
		}
		quickMenu.setLocation(location);
		quickMenu.addListener(SWT.Hide, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (!display.isDisposed()) {
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							if (!quickMenu.isDisposed()) {
								quickMenu.dispose();
							}
						}
					});
				}
			}
		});
		quickMenu.setVisible(true);
	}

	protected abstract void fillMenu(IMenuManager menu);

	protected Point computeMenuLocation(Control focus) {
		Point cursorLocation = focus.getDisplay().getCursorLocation();
		Rectangle clientArea = null;
		Point result = null;
		if (focus instanceof StyledText) {
			StyledText styledText = (StyledText) focus;
			clientArea = styledText.getClientArea();
			result = computeMenuLocation(styledText);
		} else if (focus instanceof Tree) {
			Tree tree = (Tree) focus;
			clientArea = tree.getClientArea();
			result = computeMenuLocation(tree);
		} else if (focus instanceof Table) {
			Table table = (Table) focus;
			clientArea = table.getClientArea();
			result = computeMenuLocation(table);
		}
		if (result == null) {
			result = focus.toControl(cursorLocation);
		}
		if (clientArea != null && !clientArea.contains(result)) {
			result = new Point(clientArea.x + clientArea.width / 2,
					clientArea.y + clientArea.height / 2);
		}
		Rectangle shellArea = focus.getShell().getClientArea();
		if (!shellArea.contains(focus.getShell().toControl(
				focus.toDisplay(result)))) {
			result = new Point(shellArea.x + shellArea.width / 2, shellArea.y
					+ shellArea.height / 2);
		}
		return focus.toDisplay(result);
	}

	protected Point computeMenuLocation(StyledText text) {
		Point result = text.getLocationAtOffset(text.getCaretOffset());
		result.y += text.getLineHeight(text.getCaretOffset());
		if (!text.getClientArea().contains(result)) {
			return null;
		}
		return result;
	}

	protected Point computeMenuLocation(Tree tree) {
		TreeItem[] items = tree.getSelection();
		Rectangle clientArea = tree.getClientArea();
		switch (items.length) {
		case 0:
			return null;
		case 1:
			Rectangle bounds = items[0].getBounds();
			Rectangle intersect = clientArea.intersection(bounds);
			if (intersect != null && intersect.height == bounds.height) {
				return new Point(Math.max(0, bounds.x
						+ getAvarageCharWith(tree) * CHAR_INDENT), bounds.y
						+ bounds.height);
			}
			return null;

		default:
			Rectangle[] rectangles = new Rectangle[items.length];
			for (int i = 0; i < rectangles.length; i++) {
				rectangles[i] = items[i].getBounds();
			}
			Point cursorLocation = tree.getDisplay().getCursorLocation();
			Point result = findBestLocation(getIncludedPositions(rectangles,
					clientArea), tree.toControl(cursorLocation));
			if (result != null) {
				result.x = result.x + getAvarageCharWith(tree) * CHAR_INDENT;
			}
			return result;
		}
	}

	protected Point computeMenuLocation(Table table) {
		TableItem[] items = table.getSelection();
		Rectangle clientArea = table.getClientArea();
		switch (items.length) {
		case 0: {
			return null;
		}
		case 1: {
			Rectangle bounds = items[0].getBounds(0);
			Rectangle iBounds = items[0].getImageBounds(0);
			Rectangle intersect = clientArea.intersection(bounds);
			if (intersect != null && intersect.height == bounds.height) {
				return new Point(Math.max(0, bounds.x + iBounds.width
						+ getAvarageCharWith(table) * CHAR_INDENT), bounds.y
						+ bounds.height);
			}
			return null;

		}
		default: {
			Rectangle[] rectangles = new Rectangle[items.length];
			for (int i = 0; i < rectangles.length; i++) {
				rectangles[i] = items[i].getBounds(0);
			}
			Rectangle iBounds = items[0].getImageBounds(0);
			Point cursorLocation = table.getDisplay().getCursorLocation();
			Point result = findBestLocation(getIncludedPositions(rectangles,
					clientArea), table.toControl(cursorLocation));
			if (result != null) {
				result.x = result.x + iBounds.width + getAvarageCharWith(table)
			}
			return result;
		}
		}
	}

	private Point[] getIncludedPositions(Rectangle[] rectangles,
			Rectangle widgetBounds) {
		List result = new ArrayList();
		for (int i = 0; i < rectangles.length; i++) {
			Rectangle rectangle = rectangles[i];
			Rectangle intersect = widgetBounds.intersection(rectangle);
			if (intersect != null && intersect.height == rectangle.height) {
				result.add(new Point(intersect.x, intersect.y
						+ intersect.height));
			}
		}
		return (Point[]) result.toArray(new Point[result.size()]);
	}

	private Point findBestLocation(Point[] points, Point relativeCursor) {
		Point result = null;
		double bestDist = Double.MAX_VALUE;
		for (int i = 0; i < points.length; i++) {
			Point point = points[i];
			int a = 0;
			int b = 0;
			if (point.x > relativeCursor.x) {
				a = point.x - relativeCursor.x;
			} else {
				a = relativeCursor.x - point.x;
			}
			if (point.y > relativeCursor.y) {
				b = point.y - relativeCursor.y;
			} else {
				b = relativeCursor.y - point.y;
			}
			double dist = Math.sqrt(a * a + b * b);
			if (dist < bestDist) {
				result = point;
				bestDist = dist;
			}
		}
		return result;
	}

	private int getAvarageCharWith(Control control) {
		GC gc = null;
		try {
			gc = new GC(control);
			return gc.getFontMetrics().getAverageCharWidth();
		} finally {
			if (gc != null) {
				gc.dispose();
			}
		}
	}

	@Deprecated
	public void dispose() {
	}
}
