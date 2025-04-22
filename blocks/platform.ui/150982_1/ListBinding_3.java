					@Override
					public void handleReplace(int index, S oldElement, S newElement) {
						if (useMoveAndReplace) {
							IStatus setterStatus = updateListStrategy.doReplace(destination, index,
									updateListStrategy.convert(newElement));
							mergeStatus(multiStatus, setterStatus);
