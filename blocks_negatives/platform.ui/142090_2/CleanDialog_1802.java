        projectNames.setContentProvider(new WorkbenchContentProvider());
        projectNames.setLabelProvider(new WorkbenchLabelProvider());
        projectNames.setComparator(new ResourceComparator(ResourceComparator.NAME));
        projectNames.addFilter(new ViewerFilter() {
            private final IProject[] projectHolder = new IProject[1];
            @Override
