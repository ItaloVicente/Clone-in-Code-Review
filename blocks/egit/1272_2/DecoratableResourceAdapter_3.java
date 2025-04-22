
					}
					if (resPath.isPrefixOf(wdPath)
							&& treeWalk.getFileMode(T_HEAD) == FileMode.MISSING
							&& treeWalk.getFileMode(T_INDEX) == FileMode.MISSING) {
						if (trace)
							GitTraceLocation.getTrace().trace(
									GitTraceLocation.DECORATION.getLocation(),
									"CUT"); //$NON-NLS-1$
						return false;
