	public void createLinkedSourceFolder(File directory) throws CoreException {
		IFolder folder = project.getFolder("src");
		IPath ipath = new Path(directory.getAbsolutePath());
		if (!project.getWorkspace().validateLinkLocation(folder, ipath).isOK()) {
			throw new CoreException(new Status(IStatus.ERROR, "TestProject",
					"Link location validation failed"));
		}
		folder.createLink(ipath, IResource.NONE, null);
		IPackageFragmentRoot root = javaProject.getPackageFragmentRoot(folder);
		addClassPathEntry(JavaCore.newSourceEntry(root.getPath()));
	}

