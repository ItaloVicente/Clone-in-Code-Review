				monitor.beginTask("", diffs.length); //$NON-NLS-1$
				Repository repository = commit.getRepository();
				for (FileDiff diff : diffs) {
					if (monitor.isCanceled())
						break;
					monitor.setTaskName(diff.getPath());
					try {
						formatter.write(repository, diff);
					} catch (IOException ignore) {
