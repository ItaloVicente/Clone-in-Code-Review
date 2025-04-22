		int minWidth;
		GC gc = new GC(rawTable.getDisplay());
		try {
			gc.setFont(rawTable.getFont());
		} finally {
			gc.dispose();
		}
