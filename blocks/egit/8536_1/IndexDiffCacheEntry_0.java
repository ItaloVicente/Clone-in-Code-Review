						List<String> modifiedPaths = event.getModifiedPaths();
						if (modifiedPaths.isEmpty())
							scheduleReloadJob("IndexChanged"); //$NON-NLS-1$
						else {
							Collection<IResource> res = Collections.emptyList();
							scheduleUpdateJob(modifiedPaths, res);
						}
