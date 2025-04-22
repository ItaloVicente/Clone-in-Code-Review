							@Override
							public void handleReplace(int index,
									Object oldElement, Object newElement) {
								if (useMoveAndReplace) {
									IStatus setterStatus = updateListStrategy
											.doReplace(destination, index,
													newElement);
