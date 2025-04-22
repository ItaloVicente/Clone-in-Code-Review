		} catch (ExecutionException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotEnabledException e) {
			StatusManager.getManager().handle(
					new Status(IStatus.WARNING, WorkbenchPlugin.PI_WORKBENCH,
							"Failed to execute " + IWorkbenchCommandConstants.PERSPECTIVES_SHOW_PERSPECTIVE, e)); //$NON-NLS-1$
		} catch (NotHandledException e) {
