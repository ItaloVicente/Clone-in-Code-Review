        categoryViewer.setContentProvider(new CategoryContentProvider());
        CategoryLabelProvider categoryLabelProvider = new CategoryLabelProvider(true);
        workingCopy.addActivityManagerListener(categoryLabelProvider);
        categoryViewer.setLabelProvider(categoryLabelProvider);
        categoryViewer.setComparator(new ViewerComparator());
        categoryViewer.addFilter(new EmptyCategoryFilter());
