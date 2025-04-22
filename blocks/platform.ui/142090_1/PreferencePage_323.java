		Control control = getControl();
		if (control != null) {
			size = doComputeSize();
			return size;
		}
		return new Point(0, 0);
	}

