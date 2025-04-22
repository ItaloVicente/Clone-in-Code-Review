				workTreePath = new Path(repo.getWorkTree().getAbsolutePath());
			}
			IPath path = workTreePath.append(fileDiff.getPath());
			IFile file = ResourceUtil.getFileForLocation(path, false);
			if (file != null)
				elements.add(file);
			else
				elements.add(path);
			files.add(path.toFile());
