    private String getOverlappingProjectName(String targetDirectory){
    	IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
    	IPath testPath = new Path(targetDirectory);
    	IContainer[] containers = root.findContainersForLocation(testPath);
    	if(containers.length > 0){
    		return containers[0].getProject().getName();
    	}
    	return null;
    }
