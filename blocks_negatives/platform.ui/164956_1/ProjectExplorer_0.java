			Object element = selection.getFirstElement();
			if (element instanceof IProject && !((IProject) element).isOpen()) {
				try {
					openProjectCommand.executeWithChecks(new ExecutionEvent());
				} catch (CommandException ex) {
					IStatus status = WorkbenchNavigatorPlugin.createErrorStatus("'Open Project' failed", ex); //$NON-NLS-1$
					WorkbenchNavigatorPlugin.getDefault().getLog().log(status);
