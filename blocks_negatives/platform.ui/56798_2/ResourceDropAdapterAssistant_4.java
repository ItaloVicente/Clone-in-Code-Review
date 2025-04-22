			if (o instanceof IResource) {
				selectedResources.add((IResource)o);
			} else if (o instanceof IAdaptable) {
				IAdaptable a = (IAdaptable) o;
				IResource r = a.getAdapter(IResource.class);
				if (r != null) {
					selectedResources.add(r);
				}
