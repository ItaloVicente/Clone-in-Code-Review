			for (IPath path : paths) {
				if (monitor.isCanceled()) {
					break;
				}

				if (RepositoryUtil.canBeAutoIgnored(path)) {
					addIgnore(progress.newChild(1), path);
				} else {
					progress.worked(1);
				}
