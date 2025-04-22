	private int getColumnWidth(Tree tree, String... labels) {
		GC gc = new GC(tree.getDisplay());
		try {
			gc.setFont(tree.getFont());
			int width = -1;
			for (String label : labels) {
				int w = gc.textExtent(label).x + 4;
				if (w > width) {
					width = w;
				}
			}
			return width;
		} finally {
			gc.dispose();
		}
	}

