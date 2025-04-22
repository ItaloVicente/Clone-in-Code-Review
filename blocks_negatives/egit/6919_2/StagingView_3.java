			boolean performAction = MessageDialog.openConfirm(form.getShell(),
					UIText.DeleteResourcesAction_confirmActionTitle,
					UIText.DeleteResourcesAction_confirmActionMessage);
			if (!performAction)
				return;

			List<IResource> resources = getSelectedResources();
			DeleteResourcesOperation operation = new DeleteResourcesOperation(resources);

			try {
				operation.execute(null);
			} catch (CoreException e) {
				Activator.handleError(UIText.StagingView_deleteFailed, e, true);
			}
