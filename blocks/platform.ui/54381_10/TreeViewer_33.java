				if (removed) {
					setSelection(new TreeSelection(
							(TreePath[]) oldSelection
									.toArray(new TreePath[oldSelection
											.size()]), getComparer()),
							false);
				}

