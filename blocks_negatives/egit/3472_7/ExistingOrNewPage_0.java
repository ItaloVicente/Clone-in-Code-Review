	private void updateCreateOptions() {
		minumumPath = null;
		IPath p = null;
		for (TreeItem ti : tree.getSelection()) {
			if (ti.getItemCount() > 0)
				continue;
			String path = ti.getText(2);
				p = null;
				break;
