		for (IViewReference reference : getViewReferences()) {
			ViewReference ref = (ViewReference) reference;
			if (viewId.equals(ref.getModel().getElementId())) {
				return reference;
			}
		}
		return null;
