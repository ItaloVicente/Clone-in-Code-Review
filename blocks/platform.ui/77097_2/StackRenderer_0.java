		Point location = null;
		if (forceCenter) {
			Rectangle ca = ctf.getClientArea();
			location = ctf.toDisplay(ca.x, ca.y);
			location.x = Math.max(0, (location.x + ((ca.width - size.x) / 2)));
			location.y = Math.max(0, (location.y + ((ca.height - size.y) / 3)));
		} else {
			location = ctf.toDisplay(getChevronLocation(ctf));
			Monitor mon = ctf.getMonitor();
			Rectangle bounds = mon.getClientArea();
			if (location.x + size.x > bounds.x + bounds.width) {
				location.x = bounds.x + bounds.width - size.x;
			}
			if (location.y + size.y > bounds.y + bounds.height) {
				location.y = bounds.y + bounds.height - size.y;
			}
