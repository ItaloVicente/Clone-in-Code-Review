        IContentProvider provider = contentProviderfactory.getProgressViewerContentProvider(viewer,
                false,false);
        viewer.setContentProvider(provider);
        viewer.setInput(provider);
        viewer.setLabelProvider(new ProgressViewerLabelProvider(viewerControl));
        viewer.setComparator(ProgressManagerUtil.getProgressViewerComparator());
        viewer.addFilter(new ViewerFilter() {
            @Override
