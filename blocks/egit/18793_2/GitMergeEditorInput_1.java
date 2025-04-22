				Path repositoryPath = new Path(repository.getWorkTree()
						.getAbsolutePath());
				IPath location = repositoryPath
						.append(fit.getEntryPathString());
				IFile file = ResourceUtil.getFileForLocation(location);
				if (!conflicting || useWorkspace) {
					if (file != null)
						rev = new LocalFileRevision(file);
					else
						rev = new WorkingTreeFileRevision(location.toFile());
				} else {
