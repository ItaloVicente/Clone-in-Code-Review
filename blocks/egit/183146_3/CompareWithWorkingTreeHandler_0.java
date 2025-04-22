		} else {
			Repository repo = getRepository(event);
			if (repo != null && !repo.isBare()) {
				Collection<IPath> paths = new HashSet<>();
				IResource[] resources = historyInput.getItems();
				if (resources != null) {
					Arrays.stream(resources).map(IResource::getLocation)
							.filter(Objects::nonNull).forEach(paths::add);
				}
				File[] files = historyInput.getFileList();
				if (files != null) {
					Arrays.stream(files).map(File::getAbsolutePath)
							.map(Path::fromOSString).forEach(paths::add);
				}
				GitCompareEditorInput comparison = new GitCompareEditorInput(
						null, commit.name(), repo, paths.toArray(new IPath[0]));
				CompareUtils.openInCompare(workbenchPage, comparison);
			}
