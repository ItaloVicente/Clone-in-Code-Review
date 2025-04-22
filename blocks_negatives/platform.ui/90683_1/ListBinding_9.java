								@Override
								public void handleMove(int oldIndex,
										int newIndex, Object element) {
									if (useMoveAndReplace) {
										IStatus setterStatus = updateListStrategy
												.doMove(destination, oldIndex,
														newIndex);

										mergeStatus(multiStatus, setterStatus);
									} else {
										super.handleMove(oldIndex, newIndex,
												element);
									}
								}

								@Override
								public void handleReplace(int index,
										Object oldElement, Object newElement) {
									if (useMoveAndReplace) {
										IStatus setterStatus = updateListStrategy
												.doReplace(destination, index,
														newElement);
