				RepositoryMapping mapping = RepositoryMapping
						.getMapping(source);
				IPath gitDir = mapping.getGitDirAbsolutePath();
				try {
					RepositoryProvider.unmap(source);
				} catch (TeamException e) {
					tree.failed(new Status(IStatus.ERROR, Activator
							.getPluginId(), 0,
							CoreText.MoveDeleteHook_operationError, e));
					return true; // Do not let Eclipse complete the operation
				}

				monitor.worked(100);

