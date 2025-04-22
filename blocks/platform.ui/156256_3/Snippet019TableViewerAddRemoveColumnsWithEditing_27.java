		mgr.addMenuListener(new IMenuListener() {

			@Override
			public void menuAboutToShow(IMenuManager manager) {
				if (v.getTable().getColumnCount() == 2) {
					manager.add(insertEmailBefore);
					manager.add(insertEmailAfter);
				} else {
					manager.add(removeEmail);
				}
				manager.add(configureColumns);
