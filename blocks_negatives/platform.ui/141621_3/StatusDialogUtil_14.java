		for (int i = 0; i < shells.length; i++) {
			if (shells[i].getText().equals("Problem Occurred")
					|| shells[i].getText().equals(
							"Multiple problems have occurred")) {
				if (!shells[i].isDisposed()) {
					return shells[i];
