				DiffDocument document = new DiffDocument();
				try (DiffRegionFormatter formatter = new DiffRegionFormatter(
						document)) {
					SubMonitor progress = SubMonitor.convert(monitor,
							diffs.length);
					Repository repository = commit.getRepository();
					for (FileDiff diff : diffs) {
						if (progress.isCanceled()) {
							break;
						}
						progress.subTask(diff.getPath());
						try {
							formatter.write(repository, diff);
						} catch (IOException ignore) {
						}
						progress.worked(1);
