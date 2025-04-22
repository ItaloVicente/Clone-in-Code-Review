				isBare = repo.isBare();
				if (!isBare) {
					workTreePath = new Path(
							repo.getWorkTree().getAbsolutePath());
				}
			}
			IFile file = null;
			IPath path;
			if (!isBare && workTreePath != null) {
				path = workTreePath.append(fileDiff.getPath());
				file = ResourceUtil.getFileForLocation(path, false);
				if (file != null) {
					elements.add(file);
				} else {
					elements.add(path);
				}
				files.add(path.toFile());
			} else {
				path = Path.fromPortableString(fileDiff.getGitPath());
				files.add(path.toFile());
				elements.add(fileDiff);
			}
