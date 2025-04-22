        viewer = new TreeViewer(shell);
        contentProvider = new RefreshTestTreeContentProvider();
        viewer.setContentProvider(contentProvider);
        viewer.setLabelProvider(getLabelProvider());
        return viewer;
    }
