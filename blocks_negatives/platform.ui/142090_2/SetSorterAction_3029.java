        Viewer viewer = getBrowser().getViewer();
        if (viewer instanceof StructuredViewer) {
            StructuredViewer v = (StructuredViewer) viewer;
            v.setSorter(new Sorter());
        }
    }
