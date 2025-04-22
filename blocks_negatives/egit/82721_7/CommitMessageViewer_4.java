		final IAction selectAll = new Action() {
			@Override
			public void run() {
				doOperation(ITextOperationTarget.SELECT_ALL);
			}

			@Override
			public boolean isEnabled() {
				return canDoOperation(ITextOperationTarget.SELECT_ALL);
			}
		};

		final IAction copy = new Action() {
			@Override
			public void run() {
				doOperation(ITextOperationTarget.COPY);
			}

			@Override
			public boolean isEnabled() {
				return canDoOperation(ITextOperationTarget.COPY);
			}
		};
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

