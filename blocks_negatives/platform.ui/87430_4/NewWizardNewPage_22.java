        filteredTree.getViewer().getControl().getDisplay().asyncExec(new Runnable() {
            @Override
			public void run() {
            	filteredTree.getViewer().setSelection(selection, true);
            }
        });
