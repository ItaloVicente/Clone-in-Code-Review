		filterText.addTraverseListener(new TraverseListener() {
			@Override
			public void keyTraversed(TraverseEvent e) {
				if (e.detail == SWT.TRAVERSE_RETURN) {
					e.doit = false;
					if (getViewer().getTree().getItemCount() == 0) {
						Display.getCurrent().beep();
					} else {
						boolean hasFocus = getViewer().getTree().setFocus();
						boolean textChanged = !getInitialText().equals(
								filterText.getText().trim());
						if (hasFocus && textChanged
								&& filterText.getText().trim().length() > 0) {
							Tree tree = getViewer().getTree();
							TreeItem item;
							if (tree.getSelectionCount() > 0)
								item = getFirstMatchingItem(tree.getSelection());
							else
								item = getFirstMatchingItem(tree.getItems());
							if (item != null) {
								tree.setSelection(new TreeItem[] { item });
								ISelection sel = getViewer().getSelection();
								getViewer().setSelection(sel, true);
							}
