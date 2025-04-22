
		Action toggleTreeModeAction = new Action(
				UIText.DiffEditor_OutlineTreeToggle, UIIcons.FLAT) {
			@Override
			public void run() {
				if (((DiffContentProvider) getTreeViewer().getContentProvider())
						.toggleTreeMode()) {
					setImageDescriptor(UIIcons.COMPACT);
				} else {
					setImageDescriptor(UIIcons.FLAT);
				}
				getTreeViewer().setInput(getTreeViewer().getInput());
			}
		};
