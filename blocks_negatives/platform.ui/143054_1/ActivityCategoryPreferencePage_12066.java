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
