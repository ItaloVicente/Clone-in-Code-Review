        IAdapterManager manager = Platform.getAdapterManager();
        IAdapterFactory factory = new WorkbenchAdapterFactory();
        manager.registerAdapters(factory, IWorkspace.class);
        manager.registerAdapters(factory, IWorkspaceRoot.class);
        manager.registerAdapters(factory, IProject.class);
        manager.registerAdapters(factory, IFolder.class);
        manager.registerAdapters(factory, IFile.class);
        manager.registerAdapters(factory, IMarker.class);

        IAdapterFactory paFactory = new StandardPropertiesAdapterFactory();
        manager.registerAdapters(paFactory, IWorkspace.class);
        manager.registerAdapters(paFactory, IWorkspaceRoot.class);
        manager.registerAdapters(paFactory, IProject.class);
        manager.registerAdapters(paFactory, IFolder.class);
        manager.registerAdapters(paFactory, IFile.class);
        manager.registerAdapters(paFactory, IMarker.class);
