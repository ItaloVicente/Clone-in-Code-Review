	private class DeleteAction extends Action {

		private final IStructuredSelection selection;

		DeleteAction(IStructuredSelection selection) {
			super(UIText.StagingView_DeleteItemMenuLabel);
			this.selection = selection;
		}

		@Override
		public void run() {
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
		}

		private List<IResource> getSelectedResources() {
			List<IResource> resources = new ArrayList<IResource>();
			Iterator iterator = selection.iterator();
			while (iterator.hasNext()) {
				StagingEntry stagingEntry = (StagingEntry) iterator.next();
				resources.add(stagingEntry.getFile());
			}
			return resources;
		}
	}

