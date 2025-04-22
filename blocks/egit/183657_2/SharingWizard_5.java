							if (connectAfterMove) {
								try {
									new ConnectProviderOperation(entry.getKey(),
											selectedRepository.getDirectory())
													.execute(progress
															.newChild(1));
								} catch (CoreException e) {
									throw new InvocationTargetException(e);
								}
