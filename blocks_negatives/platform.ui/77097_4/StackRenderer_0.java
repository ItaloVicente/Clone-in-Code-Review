		Point location = ctf.toDisplay(getChevronLocation(ctf));
		Monitor mon = ctf.getMonitor();
		Rectangle bounds = mon.getClientArea();
		if (location.x + size.x > bounds.x + bounds.width) {
			location.x = bounds.x + bounds.width - size.x;
		}
		if (location.y + size.y > bounds.y + bounds.height) {
			location.y = bounds.y + bounds.height - size.y;
