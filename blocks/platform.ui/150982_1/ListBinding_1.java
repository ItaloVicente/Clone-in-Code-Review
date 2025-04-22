					@Override
					public void handleAdd(int index, S element) {
						IStatus setterStatus = updateListStrategy.doAdd(destination,
								updateListStrategy.convert(element), index);
						mergeStatus(multiStatus, setterStatus);
					}
