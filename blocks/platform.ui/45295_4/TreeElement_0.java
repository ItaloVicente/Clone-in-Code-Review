package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class TreeElement extends ControlElement {
	private static boolean showedUnsupportedWarning = false;

	private final static String TREE_ARROWS_FOREGROUND_COLOR = "org.eclipse.e4.ui.css.swt.treeArrowsForegroundColor"; //$NON-NLS-1$
	private final static String TREE_ARROWS_MODE = "org.eclipse.e4.ui.css.swt.treeArrowsMode"; //$NON-NLS-1$

	private final static String TREE_ARROWS_MODE_TRIANGLE = "triangle"; //$NON-NLS-1$
	private final static String TREE_ARROWS_MODE_SQUARE = "square"; //$NON-NLS-1$

	private static abstract class TreeItemPaintListener implements Listener {
		@Override
		public void handleEvent(Event event) {
			Widget item = event.item;
			if (!(item instanceof TreeItem)) {
				return;
			}

			TreeItem treeItem = (TreeItem) item;
			if (treeItem.getItemCount() == 0) {
				return;
			}

			Tree parent = treeItem.getParent();
			boolean isCheckTree = (parent.getStyle() & SWT.CHECK) != 0;
			Object data = parent.getData(TREE_ARROWS_FOREGROUND_COLOR);
			if (!(data instanceof Color)) {
				return;
			}

			Color foreground = (Color) data;
			Color background = parent.getBackground();

			GC gc = event.gc;

			gc.setForeground(foreground);
			if (background != null) {
				gc.setBackground(background);
			}

			int baseX = isCheckTree ? (event.x - (16 * 2)) : (event.x - 16);

			gc.fillRectangle(baseX, event.y + 4, 10, 11);

			draw(treeItem, foreground, background, event, baseX);
		}

		protected abstract void draw(TreeItem treeItem, Color foreground, Color background, Event event, int baseX);
	}

	private static final TreeItemPaintListener treeItemSquaresPaintListener = new TreeItemPaintListener() {

		@Override
		protected void draw(TreeItem treeItem, Color foreground, Color background, Event event, int baseX) {
			GC gc = event.gc;

			int w = 9;
			int h = 9;
			int x = baseX;
			int y = event.y + 4;

			int halfH = h / 2;

			gc.drawRectangle(x + 1, y + 1, w - 1, h - 1);

			gc.drawLine(x + 3, y + halfH + 1, x + w - 2, y + halfH + 1);
			if (!treeItem.getExpanded()) {
				int halfW = w / 2;
				gc.drawLine(x + halfW + 1, y + 3, x + halfW + 1, y + h - 2);
			}
			event.detail &= ~SWT.BACKGROUND;
		}
	};

	private static final TreeItemPaintListener treeItemArrowsPaintListener = new TreeItemPaintListener() {

		@Override
		protected void draw(TreeItem treeItem, Color foreground, Color background, Event event, int baseX) {
			GC gc = event.gc;

			int w = 9;
			int h = 9;
			int x = baseX;
			int y = event.y + 4;

			int halfH = h / 2;

			if (!treeItem.getExpanded()) {
				int px0 = x + 1;
				int py0 = y + 1;

				int py1 = y + halfH + 1;
				int px1 = x + (w / 2) + 1;
				int py2 = y + h;

				gc.drawLine(px0, py0, px0, py2);
				gc.drawLine(px0, py0, px1, py1);
				gc.drawLine(px0, py2, px1, py1);
			} else {
				int px0 = x;
				int py0 = y;
				int px1 = x + w - 2;
				int py2 = y + h - 2;

				gc.setBackground(foreground);
				gc.fillPolygon(new int[] { px1, py0, px1, py2, px0, py2, px1, py0 });
				gc.setBackground(background);
			}
			event.detail &= ~SWT.BACKGROUND;
		}
	};

	public TreeElement(Tree composite, CSSEngine engine) {
		super(composite, engine);
	}

	public Tree getTree() {
		return (Tree) getNativeWidget();
	}

	@Override
	public void reset() {
		setTreeArrowsForegroundColor(null);
		super.reset();
	}

	private void setPaintListener(Color color) {
		if (Platform.OS_WIN32.equals(Platform.getOS())) {
			Tree tree = getTree();

			tree.removeListener(SWT.PaintItem, treeItemSquaresPaintListener);
			tree.removeListener(SWT.PaintItem, treeItemArrowsPaintListener);

			if (color != null) {
				String treeArrowsMode = getTreeArrowsMode();
				if (TREE_ARROWS_MODE_TRIANGLE.equals(treeArrowsMode)) {
					tree.addListener(SWT.PaintItem, treeItemArrowsPaintListener);
				} else if (TREE_ARROWS_MODE_SQUARE.equals(treeArrowsMode)) {
					tree.addListener(SWT.PaintItem, treeItemSquaresPaintListener);
				} else if (!showedUnsupportedWarning) {
					System.err.println("Unsupported swt-tree-arrow-mode: " + treeArrowsMode);
					showedUnsupportedWarning = true;
				}
			}
		} else if (!showedUnsupportedWarning) {
			System.err.println("swt-tree-arrow-mode and swt-tree-arrow-color are not supported on this platform");
			showedUnsupportedWarning = true;
		}
	}

	public void setTreeArrowsForegroundColor(Color color) {
		Tree tree = getTree();
		tree.setData(TREE_ARROWS_FOREGROUND_COLOR, color);
		setPaintListener(color);
	}

	public Color getTreeArrowsForegroundColor() {
		Tree tree = getTree();
		Object data = tree.getData(TREE_ARROWS_FOREGROUND_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}

	public void setTreeArrowsMode(String arrowsMode) {
		Tree tree = getTree();
		if (arrowsMode == null) {
			tree.setData(TREE_ARROWS_MODE, null);
			setPaintListener(getTreeArrowsForegroundColor());
			return;
		}
		Assert.isTrue(TREE_ARROWS_MODE_TRIANGLE.equals(arrowsMode) || TREE_ARROWS_MODE_SQUARE.equals(arrowsMode));
		tree.setData(TREE_ARROWS_MODE, arrowsMode);
	}

	public String getTreeArrowsMode() {
		Tree tree = getTree();
		Object data = tree.getData(TREE_ARROWS_MODE);
		if (TREE_ARROWS_MODE_TRIANGLE.equals(data) || TREE_ARROWS_MODE_SQUARE.equals(data)) {
			return (String) data;
		}
		return TREE_ARROWS_MODE_TRIANGLE;
	}
}
