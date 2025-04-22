				final DiffRegionFormatter formatter = new DiffRegionFormatter(
						document, document.getLength(), maxLines);

				monitor.beginTask("", diffs.size()); //$NON-NLS-1$
				for (FileDiff diff : diffs) {
					if (monitor.isCanceled()) {
						break;
					}
					if (diff.getCommit().getParentCount() > 1) {
						break;
					}
					if (document.getNumberOfLines() > maxLines) {
						break;
