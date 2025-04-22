			tree.setInput(potentialProjects);
			tree.setCheckedElements(this.notAlreadyExistingProjects.toArray());
		} catch (InvocationTargetException ite) {
			this.selection = null;
			IStatus status = new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, DataTransferMessages.SmartImportWizardPage_scanProjectsFailed,
					ite.getCause());
			StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.SHOW);
		} catch (InterruptedException operationCanceled) {
