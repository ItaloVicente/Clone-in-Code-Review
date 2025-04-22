    protected void setDetails(ICategory category) {
        if (category == null) {
            clearDetails();
            return;
        }
        Set categories = null;
        if (WorkbenchActivityHelper.isEnabled(workingCopy, category.getId())) {
            categories = WorkbenchActivityHelper.getDisabledCategories(
                    workingCopy, category.getId());

        } else {
            categories = WorkbenchActivityHelper.getEnabledCategories(
                    workingCopy, category.getId());
        }

        categories = WorkbenchActivityHelper.getContainedCategories(
                workingCopy, category.getId());
        dependantViewer.setInput(categories);
        try {
            descriptionText.setText(category.getDescription());
        } catch (NotDefinedException e) {
        }
    }

    /**
     * Clear the details area.
     */
    protected void clearDetails() {
        dependantViewer.setInput(Collections.EMPTY_SET);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
        this.workbench = workbench;
        workingCopy = workbench.getActivitySupport().createWorkingCopy();
        setPreferenceStore(WorkbenchPlugin.getDefault().getPreferenceStore());
    }

    /**
     * Return whether the category is locked.
     * 
     * @param category
     *            the category to test
     * @return whether the category is locked
     */
    protected boolean isLocked(ICategory category) {
        return !WorkbenchActivityHelper.getDisabledCategories(workingCopy,
                category.getId()).isEmpty();
    }

    @Override
