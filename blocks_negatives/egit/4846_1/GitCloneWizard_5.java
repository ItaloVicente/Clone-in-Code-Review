		cloneDestination = new CloneDestinationPage() {
			@Override
			public void setVisible(boolean visible) {
				if (visible)
					setSelection(cloneSource.getSelection(), validSource
							.getAvailableBranches(), validSource
							.getSelectedBranches(), validSource.getHEAD());
				super.setVisible(visible);
			}
		};
