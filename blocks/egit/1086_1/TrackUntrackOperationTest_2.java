			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile)
					files.add((IFile) resource);
				return true;
			}
		});

		IFile[] fileArr = files.toArray(new IFile[files.size()]);

		assertTrackedState(fileArr, false);

		TrackOperation trop = new TrackOperation(projectArr);
