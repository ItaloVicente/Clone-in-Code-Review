        viewer = new PropertySheetViewer(parent);
        viewer.setSorter(sorter);

        if (rootEntry == null) {
            PropertySheetEntry root = new PropertySheetEntry();
            if (provider != null) {
