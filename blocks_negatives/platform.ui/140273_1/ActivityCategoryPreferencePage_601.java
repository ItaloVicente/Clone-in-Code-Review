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
