package org.eclipse.e4.ui.css.swt.dom;

import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class TreeElement extends ControlElement {

	private final static String TREE_ARROWS_FOREGROUND_COLOR = "org.eclipse.e4.ui.css.swt.treeArrowsForegroundColor"; //$NON-NLS-1$

	private static final Listener treeItemPaintListener = new Listener() {

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
			Object data = parent.getData(TREE_ARROWS_FOREGROUND_COLOR);
			if (!(data instanceof Color)) {
				return;
			}
			Color foreground = (Color) data;
			Color background = parent.getBackground();

			int w = 9;
			int h = 9;
			int x = event.x - 16;
			int y = event.y + 4;
			GC gc = event.gc;

			gc.setForeground(foreground);
			if (background != null) {
				gc.setBackground(background);
			}

			gc.fillRectangle(x, y, w + 1, h + 2);
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

	public void setTreeArrowsForegroundColor(Color color) {
		Tree tree = getTree();
		tree.setData(TREE_ARROWS_FOREGROUND_COLOR, color);

		if (Constants.OS_WIN32.equals(Platform.getOS())) {

			tree.removeListener(SWT.PaintItem, treeItemPaintListener);
			if (color != null) {
				tree.addListener(SWT.PaintItem, treeItemPaintListener);
			}
		}
	}

	public Color getTreeArrowsForegroundColor() {
		Tree tree = getTree();
		Object data = tree.getData(TREE_ARROWS_FOREGROUND_COLOR);
		if (data instanceof Color) {
			return (Color) data;
		}
		return null;
	}
}
