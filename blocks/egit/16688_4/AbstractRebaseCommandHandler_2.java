					@Override
					public void aboutToRun(IJobChangeEvent event) {
						if (operation == Operation.BEGIN
								&& !repository.getRepositoryState().equals(
										RepositoryState.SAFE)) {
							throw new IllegalStateException(
									"Can't start rebase if repository state isn't SAFE"); //$NON-NLS-1$
						}
						super.aboutToRun(event);
					}

