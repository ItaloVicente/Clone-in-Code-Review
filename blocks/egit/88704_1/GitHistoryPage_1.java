				try (DiffRegionFormatter formatter = new DiffRegionFormatter(
						document, document.getLength(), maxLines)) {
					SubMonitor progress = SubMonitor.convert(monitor,
							diffs.size());
					for (FileDiff diff : diffs) {
						if (progress.isCanceled()
								|| diff.getCommit().getParentCount() > 1
								|| document.getNumberOfLines() > maxLines) {
							break;
						}
						progress.subTask(diff.getPath());
						try {
							formatter.write(repository, diff);
						} catch (IOException ignore) {
						}
						progress.worked(1);
