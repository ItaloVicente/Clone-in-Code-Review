                return wizardElement.createWizard();
            }
        };
    }

    /**
     * Uses the dialog store to restore widget values to the values that they
     * held last time this wizard was used to completion.
     */
    protected void restoreWidgetValues() {
        updateMessage();
    }

    /**
     * Expands the wizard categories in this page's category viewer that were
     * expanded last time this page was used. If a category that was previously
     * expanded no longer exists then it is ignored.
     */
    protected void expandPreviouslyExpandedCategories(String setting, IWizardCategory wizardCategories, TreeViewer viewer) {
        String[] expandedCategoryPaths =  getDialogSettings()
                .getArray(setting);
        if (expandedCategoryPaths == null || expandedCategoryPaths.length == 0) {
