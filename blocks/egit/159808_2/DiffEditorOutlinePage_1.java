
		Action toggleTreeModeAction = new Action(
				UIText.DiffEditor_OutlineTreeToggle) {
			@Override
			public void run() {
				updateOutlineTreeMode(true, this);
			}
		};
		updateOutlineTreeMode(false, toggleTreeModeAction);
