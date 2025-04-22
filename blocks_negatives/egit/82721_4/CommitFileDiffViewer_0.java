		if (site instanceof IPageSite) {
			final IPageSite pageSite = (IPageSite) site;
			getControl().addFocusListener(new FocusListener() {
				@Override
				public void focusLost(FocusEvent e) {
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.SELECT_ALL.getId(), null);
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.COPY.getId(), null);
					pageSite.getActionBars().getMenuManager().update(false);
				}

				@Override
				public void focusGained(FocusEvent e) {
					updateActionEnablement(getSelection());
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.SELECT_ALL.getId(), selectAll);
					pageSite.getActionBars().setGlobalActionHandler(
							ActionFactory.COPY.getId(), copy);
					pageSite.getActionBars().getMenuManager().update(false);
				}
			});
		}
