        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            @Override
			public void selectionChanged(SelectionChangedEvent event) {
                TaskList.this.selectionChanged(event);
            }
        });
        viewer.addOpenListener(new IOpenListener() {
            @Override
			public void open(OpenEvent event) {
                gotoTaskAction.run();
            }
        });
