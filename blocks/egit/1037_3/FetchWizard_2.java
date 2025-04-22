		refSpecPage = new RefSpecPage(localDb, false) {
			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(repoPage.getSelection());
				super.setVisible(visible);
			}
		};
