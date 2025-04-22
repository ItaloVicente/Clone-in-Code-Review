		txtQuickAccess.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				IHandlerService hs = SearchField.this.window.getContext().get(IHandlerService.class);
				if (commandProvider.getContextSnapshot() == null) {
					commandProvider.setSnapshot(hs.createContextSnapshot(true));
				}
			}
		});
