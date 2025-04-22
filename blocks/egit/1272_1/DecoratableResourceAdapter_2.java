					if (resource.getFullPath().isPrefixOf(
							resourceEntry.getResource().getFullPath())
							&& treeWalk.getFileMode(T_HEAD) == FileMode.MISSING
							&& treeWalk.getFileMode(T_INDEX) == FileMode.MISSING) {
						GitTraceLocation.getTrace().trace(
								GitTraceLocation.DECORATION.getLocation(),
								"CUT"); //$NON-NLS-1$
						return false;
					}

