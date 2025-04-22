			tv.getControl().addTraverseListener(new TraverseListener() {
				public void keyTraversed(TraverseEvent e) {
					if (tv.getTable().getSelection().length == 0) {
						if (tv.getTable().getItemCount() > 0) {
							tv.setSelection(new StructuredSelection(tv
									.getTable().getItem(0)));
						}
					}
				}
			});
