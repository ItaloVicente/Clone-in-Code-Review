		if (size != null) {
			return size;
		}
		Control control = getControl();
		if (control != null) {
			size = doComputeSize();
