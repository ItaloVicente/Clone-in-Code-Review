    /**
     * Creates a new project.
     *
     * @param name the project name
     */
    public static IProject createProject(String name) throws CoreException {
        IWorkspace ws = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = ws.getRoot();
        IProject proj = root.getProject(name);
        if (!proj.exists())
            proj.create(null);
        if (!proj.isOpen())
            proj.open(null);
        return proj;
    }
