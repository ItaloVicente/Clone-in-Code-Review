			if (innerWidth < 0)
				innerWidth = 0;
		}
		int innerHeight = hHint;
		if (innerHeight != SWT.DEFAULT) {
			innerHeight -= marginHeight * 2;
			if (innerHeight < 0)
				innerHeight = 0;
		}
		Point textSize = computeTextSize(innerWidth, innerHeight);
