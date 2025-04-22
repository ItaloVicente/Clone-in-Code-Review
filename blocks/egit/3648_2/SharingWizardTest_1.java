			RepositoryMapping repo = RepositoryMapping.getMapping(project);
			if (repo != null) {
				IPath gitDirAbsolutePath = repo.getGitDirAbsolutePath();
				File canonicalFile = gitDirAbsolutePath.toFile().getCanonicalFile();
				dirs.add(canonicalFile);
				File workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toFile().getCanonicalFile();
				File gitDirParent = canonicalFile.getParentFile();
				if (!(gitDirParent.toString()+"/").startsWith(workspacePath.toString()+"/"))
					if (!(gitDirParent.toString()+"/").startsWith(getTestDirectory().getCanonicalPath().toString()+"/"))
						fail("Attempting cleanup of directory neither in workspace nor test directory" + canonicalFile);
				new DisconnectProviderOperation(Collections.singleton(project)).execute(null);
			}
