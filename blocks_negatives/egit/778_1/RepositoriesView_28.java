
			final RepositoryTreeNode selNode = currentNode;

			Display.getDefault().asyncExec(new Runnable() {

				public void run() {
					tv.setSelection(new StructuredSelection(selNode), true);
				}
			});

