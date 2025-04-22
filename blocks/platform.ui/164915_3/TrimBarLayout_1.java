	private boolean isEmptyToolbar(Control ctrl) {
		if (!(ctrl instanceof ToolBar) && ctrl instanceof Composite) {
			Control[] kids = ((Composite) ctrl).getChildren();
			if (kids.length == 1) {
				ctrl = kids[0];
			}
		}
