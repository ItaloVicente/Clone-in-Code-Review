    public void run() {
    	List<? extends IResource> selectedResources = getSelectedResources();
    	IResource[] resources = selectedResources.toArray(new IResource[selectedResources.size()]);

    	final int length = resources.length;
    	int actualLength = 0;
    	String[] fileNames = new String[length];
    	StringBuilder buf = new StringBuilder();
    	for (int i = 0; i < length; i++) {
    		IPath location = resources[i].getLocation();
    		if (location != null) {
    			fileNames[actualLength++] = location.toOSString();
    		}
    		if (i > 0) {
    			buf.append("\n"); //$NON-NLS-1$
    		}
    		buf.append(resources[i].getName());
    	}
    	if (actualLength < length) {
    		String[] tempFileNames = fileNames;
    		fileNames = new String[actualLength];
    		System.arraycopy(tempFileNames, 0, fileNames, 0, actualLength);
    	}
    	setClipboard(resources, fileNames, buf.toString());

    	if (pasteAction != null && pasteAction.getStructuredSelection() != null) {
    		pasteAction.selectionChanged(pasteAction.getStructuredSelection());
    	}
