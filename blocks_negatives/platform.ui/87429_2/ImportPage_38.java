		importTree.getViewer().addSelectionChangedListener(new ISelectionChangedListener(){
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				listSelectionChanged(event.getSelection());
			}
		});
		importTree.getViewer().addDoubleClickListener(new IDoubleClickListener(){
	    	@Override
			public void doubleClick(DoubleClickEvent event) {
	    		treeDoubleClicked(event);
	    	}
	    });
