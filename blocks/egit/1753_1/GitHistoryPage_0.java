		if (object instanceof IResource) {
			return typeOk((IResource) object);
		}
		if (object instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) object)
					.getAdapter(IResource.class);
			return resource == null ? false : typeOk(resource);
		}
		if (object instanceof HistoryPageInput) {
			HistoryPageInput in = (HistoryPageInput) object;
			if (in.getRepository() != null)
				return true;
			final IResource[] array = ((HistoryPageInput) object).getItems();
