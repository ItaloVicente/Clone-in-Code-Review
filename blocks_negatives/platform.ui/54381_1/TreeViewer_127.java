				if (removedPath != null) {
					boolean removed = false;
					for (Iterator it = oldSelection.iterator(); it
							.hasNext();) {
						TreePath path = (TreePath) it.next();
						if (path.startsWith(removedPath, getComparer())) {
							it.remove();
							removed = true;
						}
					}
					if (removed) {
						setSelection(new TreeSelection(
								(TreePath[]) oldSelection
										.toArray(new TreePath[oldSelection
												.size()]), getComparer()),
								false);
