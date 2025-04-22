	public void init(IActionBars bars) {
		this.bars = bars;
		contributeToMenu(bars.getMenuManager());
		contributeToToolBar(bars.getToolBarManager());
		if (bars instanceof IActionBars2) {
			contributeToCoolBar(((IActionBars2) bars).getCoolBarManager());
		}
		contributeToStatusLine(bars.getStatusLineManager());
