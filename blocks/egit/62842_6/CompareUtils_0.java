			final IResource file, final String refName, final IWorkbenchPage page) {
		if (file == null) {
			return;
		}
		final IPath location = file.getLocation();
		if(location == null){
			return;
		}
