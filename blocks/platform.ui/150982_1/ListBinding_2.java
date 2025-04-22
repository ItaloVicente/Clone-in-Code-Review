					@Override
					public void handleMove(int oldIndex, int newIndex, S element) {
						if (useMoveAndReplace) {
							IStatus setterStatus = updateListStrategy.doMove(destination, oldIndex, newIndex);
							mergeStatus(multiStatus, setterStatus);
						} else {
							super.handleMove(oldIndex, newIndex, element);
						}
					}
