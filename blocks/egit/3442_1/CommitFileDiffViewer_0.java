		if (site instanceof IPageSite) {
			final IPageSite pageSite = (IPageSite) site;
			getControl().addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.SELECT_ALL.getId(), null);
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.COPY.getId(), null);
					pageSite.getActionBars().updateActionBars();
				}
