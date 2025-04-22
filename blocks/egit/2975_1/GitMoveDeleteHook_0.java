			try {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(source);
				IPath gitDir = newLocation.append(mapping.getGitDir());
				RepositoryProvider.unmap(source);

				monitor.worked(100);
				tree.standardMoveProject(source, description, updateFlags,
						monitor);

				reconnect(source, description, gitDir, monitor);
			} catch (CoreException e) {
				tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(),
						0, CoreText.MoveDeleteHook_operationError, e));
			}
			return true;
		}
