        list.setContentProvider(new PerspContentProvider());
        list.addFilter(activityViewerFilter);
        list.setComparator(new ViewerComparator());
        list.setInput(perspReg);
        list.addSelectionChangedListener(this);
        list.addDoubleClickListener(event -> handleDoubleClickEvent());
