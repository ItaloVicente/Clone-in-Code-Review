		refSpecPage = new RefSpecPage(localDb, true) {
			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(repoPage.getSelection());
				super.setVisible(visible);
			}
		};
		confirmPage = new ConfirmationPage(localDb);
