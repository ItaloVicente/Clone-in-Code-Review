		projectsList.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				ProjectRecord element = (ProjectRecord) event.getElement();
				if (element.hasConflicts || element.isInvalid) {
					projectsList.setChecked(element, false);
				}
				setPageComplete(projectsList.getCheckedElements().length > 0);
