
			tree.standardMoveProject(source, description, updateFlags,
					monitor);

			mapProject(
					source.getWorkspace().getRoot()
							.getProject(description.getName()),
					description, monitor, gitDir);
		} catch (IOException e) {
			tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(),
					0, CoreText.MoveDeleteHook_operationError, e));
		} catch (CoreException e) {
			tree.failed(new Status(IStatus.ERROR, Activator.getPluginId(),
					0, CoreText.MoveDeleteHook_operationError, e));
