							@Override
							public void handleAdd(int index, Object element) {
								IStatus setterStatus = updateListStrategy.doAdd(destination,
										updateListStrategy.convert(element), index);

								mergeStatus(multiStatus, setterStatus);
