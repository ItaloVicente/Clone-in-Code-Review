        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IProject testProject = workspace.getRoot().getProject(getName());
        testProject.create(null);
        testProject.open(null);

        IFile iFile = testProject.getFile(fileName);
        iFile.create(new ByteArrayInputStream(new byte[] { '\n' }), true, null);
        return iFile;
