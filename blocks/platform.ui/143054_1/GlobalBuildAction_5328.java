			}
		} catch (CoreException e) {
			StatusManager.getManager().handle(e, IDEWorkbenchPlugin.IDE_WORKBENCH);
			ErrorDialog
					.openError(
							getShell(),
							IDEWorkbenchMessages.GlobalBuildAction_buildProblems,
							NLS.bind(IDEWorkbenchMessages.GlobalBuildAction_internalError, e.getMessage()),
							e.getStatus());
			return false;
		}
		return false;
	}
