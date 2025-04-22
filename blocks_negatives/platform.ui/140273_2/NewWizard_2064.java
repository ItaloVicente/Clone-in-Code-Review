        IWizardCategory root = WorkbenchPlugin.getDefault().getNewWizardRegistry().getRootCategory();
        IWizardDescriptor [] primary = WorkbenchPlugin.getDefault().getNewWizardRegistry().getPrimaryWizards();

        if (categoryId != null) {
            IWizardCategory categories = root;
            StringTokenizer familyTokenizer = new StringTokenizer(categoryId,
                    CATEGORY_SEPARATOR);
            while (familyTokenizer.hasMoreElements()) {
                categories = getChildWithID(categories, familyTokenizer
                        .nextToken());
                if (categories == null) {
