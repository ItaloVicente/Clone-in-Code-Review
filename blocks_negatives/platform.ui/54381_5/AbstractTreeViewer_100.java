		preservingSelection(new Runnable() {
			@Override
			public void run() {
	            Control tree = getControl();
	            tree.setRedraw(false);
	            try {
	                removeAll(tree);
	                tree.setData(getRoot());
	                internalInitializeTree(tree);
	            } finally {
	                tree.setRedraw(true);
	            }
			}
