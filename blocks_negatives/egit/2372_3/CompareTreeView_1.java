			public void treeCollapsed(TreeExpansionEvent event) {
				reactOnCollapse(event);
			}
		});

		rightTree.addOpenListener(new IOpenListener() {
