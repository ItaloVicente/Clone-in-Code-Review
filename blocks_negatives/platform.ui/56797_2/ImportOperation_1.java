        if (resource instanceof IFile) {
            return (IFile) resource;
        }
        Object adapted = ((IAdaptable) resource).getAdapter(IFile.class);
        if(adapted == null) {
			return null;
		}
        return (IFile) adapted;

