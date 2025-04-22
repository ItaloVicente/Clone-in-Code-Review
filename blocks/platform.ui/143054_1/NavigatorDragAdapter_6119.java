
		ISelection selection = selectionProvider.getSelection();
		if (!(selection instanceof IStructuredSelection) || selection.isEmpty()) {
			return null;
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;

		Iterator itr = structuredSelection.iterator();
		while (itr.hasNext()) {
			Object obj = itr.next();
			if (obj instanceof IResource) {
				IResource res = (IResource) obj;
				if ((res.getType() & resourceTypes) == res.getType()) {
					resources.add(res);
				}
			}
		}
		IResource[] result = new IResource[resources.size()];
		resources.toArray(result);
		return result;
	}
