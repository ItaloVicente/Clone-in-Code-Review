		IMarker selectedMarker = (IMarker) o;
		IResource resource = selectedMarker.getResource();
		if (resource instanceof IFile) {
			marker = selectedMarker;
			setEnabled(true);
		}
	}
