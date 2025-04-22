
		if (IWorkbenchAdapter.class == adapterType) {
			if (gitModelWorkbenchAdapter == null)
				gitModelWorkbenchAdapter = new GitModelWorkbenchAdapter();
			return gitModelWorkbenchAdapter;
		}

		if (adaptableObject instanceof GitModelObject
				&& adapterType == ResourceMapping.class)
			return GitObjectMapping.create((GitModelObject) adaptableObject);

		if (adaptableObject instanceof GitModelObject
				&& adapterType == IResource.class) {
			GitModelObject obj = (GitModelObject) adaptableObject;

			if (obj instanceof GitModelBlob)
				return root.getFileForLocation(obj.getLocation());

			if (obj instanceof GitModelTree)
				return root.getContainerForLocation(obj.getLocation());
		}

