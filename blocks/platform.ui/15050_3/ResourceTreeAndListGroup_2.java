		public void treeExpanded(TreeExpansionEvent event) {
			expandTreeElement(event.getElement());
		}
	}
	
	private CheckListener checkListener = new CheckListener();
	private SelectionListener selectionListener = new SelectionListener();
	private TreeListener treeListener = new TreeListener();
	
	private Object root;
