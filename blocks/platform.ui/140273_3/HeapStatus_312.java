		GC gc = new GC(this);
		Point p = gc.textExtent(WorkbenchMessages.HeapStatus_widthStr);
		int height = imgBounds.height;
		height = Math.max(height, p.y) + 4;
		height = Math.max(TrimUtil.TRIM_DEFAULT_HEIGHT, height);
		gc.dispose();
