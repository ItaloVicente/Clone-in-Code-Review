	public boolean show(ShowInContext context) {
		ISelection selection = context.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			if (ss.size() == 1) {
				Object element = ss.getFirstElement();
				if (element instanceof IAdaptable) {
					IResource resource = (IResource) ((IAdaptable) element)
							.getAdapter(IResource.class);
					if (resource != null) {
						showResource(resource);
						return true;
					}
				}
			}
		}
		return false;
