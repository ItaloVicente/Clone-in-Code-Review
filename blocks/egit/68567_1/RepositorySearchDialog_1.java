			for (File child : children) {
				if (monitor.isCanceled())
					return;
				if (child.isDirectory()
						&& !child.equals(new File(root, Constants.DOT_GIT))) {
					monitor.subTask(child.getPath());
					findGitDirsRecursive(child, strings, monitor,
							lookForNestedRepositories);
				}
