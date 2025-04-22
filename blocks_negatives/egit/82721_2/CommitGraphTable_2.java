		final IAction selectAll = createStandardAction(ActionFactory.SELECT_ALL);
		getControl().addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.SELECT_ALL.getId(), null);
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), null);
				site.getActionBars().getMenuManager().update(false);
			}

			@Override
			public void focusGained(FocusEvent e) {
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.SELECT_ALL.getId(), selectAll);
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), copy);
				site.getActionBars().getMenuManager().update(false);
			}
		});
