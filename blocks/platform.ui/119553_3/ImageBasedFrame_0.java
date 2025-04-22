		if (!framedControl.isDisposed()) {
			if (vertical) {
				framedControl.setLocation(w1, h1 + handleHeight);
			} else {
				framedControl.setLocation(w1 + handleWidth, h1);
			}
