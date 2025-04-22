							Map attrs = new HashMap();
							attrs.put(IMarker.DONE, Boolean.TRUE);
							IUndoableOperation op = new UpdateMarkersOperation(
									markers, attrs,
									MarkerMessages.markCompletedAction_title,
									true);
