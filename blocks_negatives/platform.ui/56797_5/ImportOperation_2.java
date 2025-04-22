        if (resource instanceof IFolder) {
            return (IFolder) resource;
        }
        Object adapted = ((IAdaptable) resource).getAdapter(IFolder.class);
        if(adapted == null) {
			return null;
		}
        return (IFolder) adapted;
