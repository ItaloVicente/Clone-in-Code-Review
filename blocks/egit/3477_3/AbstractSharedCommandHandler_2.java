			IEditorInput activeEditor = WORKBENCH.getActiveWorkbenchWindow()
					.getActivePage().getActiveEditor().getEditorInput();
			IResource resource = (IResource) activeEditor
					.getAdapter(IResource.class);

			if (resource != null)
				return RepositoryMapping.getMapping(resource).getRepository();
