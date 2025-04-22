	}

	private void paintCompositeMaxUnknown(GC gc) {
		Rectangle rect = getClientArea();
		int x = rect.x;
		int y = rect.y;
		int w = rect.width;
		int h = rect.height;
		int bw = imgBounds.width; // button width
		int dx = x + w - bw - 2; // divider x
		int sw = w - bw - 3; // status width
		int uw = (int) (sw * usedMem / totalMem); // used mem width
		int ux = x + 1 + uw; // used mem right edge
		if (bgCol != null) {
