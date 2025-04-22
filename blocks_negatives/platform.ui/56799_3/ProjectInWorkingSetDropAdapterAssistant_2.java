					if (path.getFirstSegment() instanceof IAdaptable) {
						IWorkingSet sourceWorkingSet = ((IAdaptable) path.getFirstSegment())
								.getAdapter(IWorkingSet.class);
						if (sourceWorkingSet != null) {
							removeFromWorkingSet(project, sourceWorkingSet);
						}
