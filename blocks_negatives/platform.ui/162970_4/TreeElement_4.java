public class TreeElement extends ControlElement
implements ISelectionBackgroundCustomizationElement, IHeaderCustomizationElement {

	private static boolean showedUnsupportedWarning = false;



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
			Color background = null;
			if ((event.detail & SWT.SELECTED) != 0) {
				background = ControlSelectedColorCustomization.getSelectionBackgroundColor(parent);

			} else if ((event.detail & SWT.HOT) != 0) {
				background = ControlSelectedColorCustomization.getHotBackgroundColor(parent);

			}
			if(background == null){
				background = parent.getBackground();
			}

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

	/**
	 * A painter to draw squares as tree arrows.
	 */
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

	/**
	 * A painter to draw triangles as tree arrows.
	 */
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

	private static class TreeControlSelectionEraseListener extends AbstractControlSelectionEraseListener {

		@Override
		protected void fixEventDetail(Control control, Event event) {
			event.detail &= ~SWT.SELECTED;
		}

		@Override
		protected int getNumberOfColumns(Control control) {
			return ((Tree) control).getColumnCount();
		}

	}

	private final ControlSelectedColorCustomization fControlSelectedColorCustomization;
