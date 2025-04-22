			for (File child : children) {
				if (monitor.isCanceled()) {
					return;
				}
				if (child.isDirectory()
						&& !Constants.DOT_GIT.equals(child.getName())) {
					monitor.subTask(child.getPath());
					findGitDirsRecursive(child, strings, monitor, depth - 1);
				}
