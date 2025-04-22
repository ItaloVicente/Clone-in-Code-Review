				this, unstaged) {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput,
					Object newInput) {
				super.inputChanged(viewer, oldInput, newInput);
				if (unstaged) {
					stageAllAction.setEnabled(getCount() > 0);
					unstagedToolBarManager.update(true);
				} else {
					unstageAllAction.setEnabled(getCount() > 0);
					stagedToolBarManager.update(true);
				}
			}
		};
