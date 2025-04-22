				try {
					RepositoryProvider.unmap(source);
				} catch (TeamException e) {
					tree.failed(new Status(IStatus.ERROR, Activator
							.getPluginId(), 0,
							CoreText.MoveDeleteHook_operationError, e));
				}
