		if (bars instanceof IActionBars2) {
			ab = new SubActionBars2((IActionBars2) bars, this);
		} else {
			ab = new SubActionBars(bars, this);
		}
	}
