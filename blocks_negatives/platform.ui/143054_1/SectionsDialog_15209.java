        List list = new List(composite, SWT.BORDER);
        GridData data = new GridData(GridData.FILL_BOTH);
        list.setLayoutData(data);
        ListViewer viewer = new ListViewer(list);
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(input);
