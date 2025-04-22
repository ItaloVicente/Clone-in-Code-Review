		if (element instanceof IAdaptable) {
			IAdaptable adaptable = (IAdaptable) element;
			T o = adaptable.getAdapter(adapterType);
	        if (o != null) {
	        	return o;
	        }
		}
		if (forceLoad) {
			return adapterType.cast(Platform.getAdapterManager().loadAdapter(element, adapterType.getName()));
		}
		return Platform.getAdapterManager().getAdapter(element, adapterType);
