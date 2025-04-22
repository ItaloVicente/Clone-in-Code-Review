	private int getColumnWidth(Tree tree, String... labels) {
		GC gc = new GC(tree.getDisplay());
		try {
			gc.setFont(tree.getFont());
			int width = -1;
			for (String label : labels) {
				int w = gc.textExtent(label).x;
				if (w > width) {
					width = w;
				}
			}
			return width + 8;
		} finally {
			gc.dispose();
		}
	}

