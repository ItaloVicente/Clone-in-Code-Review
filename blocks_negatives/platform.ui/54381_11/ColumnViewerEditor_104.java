					tabeditingListener = new TraverseListener() {

						@Override
						public void keyTraversed(TraverseEvent e) {
							if ((feature & DEFAULT) != DEFAULT) {
								processTraverseEvent(cell.getColumnIndex(),
										viewer.getViewerRowFromItem(cell
												.getItem()), e);
							}
