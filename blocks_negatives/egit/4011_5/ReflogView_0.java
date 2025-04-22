		if (selection instanceof StructuredSelection) {
			StructuredSelection ssel = (StructuredSelection) selection;
			if (ssel.size() != 1)
				return;
			Repository repository = null;
			if (ssel.getFirstElement() instanceof IResource) {
				IResource resource = (IResource) ssel.getFirstElement();
