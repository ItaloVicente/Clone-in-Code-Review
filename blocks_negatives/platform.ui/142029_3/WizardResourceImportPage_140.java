        IPath containerPath = getContainerFullPath();
        if (containerPath == null) {
            setMessage(EMPTY_FOLDER_MESSAGE);
            return false;
        }

        IContainer container = getSpecifiedContainer();
        if (container == null) {
        	if(IDEWorkbenchPlugin.getPluginWorkspace().getRoot().exists(getContainerFullPath())) {
