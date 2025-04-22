		MenuItem mi = null;
		if (index >= 0) {
			mi = new MenuItem(parent, flags, index);
		} else {
			mi = new MenuItem(parent, flags);
		}
		widget = mi;
