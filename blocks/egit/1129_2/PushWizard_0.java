		confirmPage = new ConfirmationPage(localDb) {
			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(repoPage.getSelection(), refSpecPage
							.getRefSpecs());
				super.setVisible(visible);
			}
		};
