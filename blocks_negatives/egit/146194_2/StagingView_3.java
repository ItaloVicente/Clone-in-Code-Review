		listPresentationAction = new Action(UIText.StagingView_List,
				IAction.AS_RADIO_BUTTON) {
			@Override
			public void run() {
				if (!isChecked()) {
					return;
				}
				switchToListMode();
				refreshViewers();
			}
		};
		listPresentationAction.setImageDescriptor(UIIcons.FLAT);
