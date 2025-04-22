			gc.fillRectangle(event.x - 16, event.y + 4, 10, 11);

			draw(treeItem, foreground, background, event);
		}

		protected abstract void draw(TreeItem treeItem, Color foreground, Color background, Event event);
	}

	private static final TreeItemPaintListener treeItemSquaresPaintListener = new TreeItemPaintListener() {

		@Override
		protected void draw(TreeItem treeItem, Color foreground, Color background, Event event) {
			GC gc = event.gc;

			int w = 9;
			int h = 9;
			int x = event.x - 16;
			int y = event.y + 4;

