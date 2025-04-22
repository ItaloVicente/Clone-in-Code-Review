	protected void setDetails(ICategory category) {
		if (category == null) {
			clearDetails();
			return;
		}
		Set<String> categories = null;
		if (WorkbenchActivityHelper.isEnabled(workingCopy, category.getId())) {
			categories = WorkbenchActivityHelper.getDisabledCategories(workingCopy, category.getId());

		} else {
			categories = WorkbenchActivityHelper.getEnabledCategories(workingCopy, category.getId());
		}

		categories = WorkbenchActivityHelper.getContainedCategories(workingCopy, category.getId());
		dependantViewer.setInput(categories);
		try {
			descriptionText.setText(category.getDescription());
		} catch (NotDefinedException e) {
			descriptionText.setText(""); //$NON-NLS-1$
		}
	}

	protected void clearDetails() {
		dependantViewer.setInput(Collections.EMPTY_SET);
		descriptionText.setText(""); //$NON-NLS-1$
	}

	@Override
	public void init(IWorkbench workbench) {
		this.workbench = workbench;
		workingCopy = workbench.getActivitySupport().createWorkingCopy();
		setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
	}

	protected boolean isLocked(ICategory category) {
		return !WorkbenchActivityHelper.getDisabledCategories(workingCopy, category.getId()).isEmpty();
	}
