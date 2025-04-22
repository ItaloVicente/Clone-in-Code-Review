
		compactTreePresentationAction = new Action(UIText.StagingView_CompactTree,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				switchToCompactModeInternal(false);
				refreshViewers();
			}

		};
		compactTreePresentationAction.setImageDescriptor(UIIcons.COMPACT);
