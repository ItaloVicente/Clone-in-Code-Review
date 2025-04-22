	private void initializeScrollBars() {
		ScrollBar hbar = getHorizontalBar();
		if (hbar != null) {
			hbar.setIncrement(H_SCROLL_INCREMENT);
		}
		ScrollBar vbar = getVerticalBar();
		if (vbar != null) {
			vbar.setIncrement(V_SCROLL_INCREMENT);
		}
		updatePageIncrement();
	}
