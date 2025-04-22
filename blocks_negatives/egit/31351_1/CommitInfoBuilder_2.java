
	private void buildDiffs(final StringBuilder d,
			final List<StyleRange> styles, IProgressMonitor monitor,
			boolean trace) throws OperationCanceledException,
			IOException {

		final String[] currentEncoding = new String[1];

		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
		if (commit.getParentCount() > 1) {
			d.append(UIText.CommitMessageViewer_CanNotRenderDiffMessage);
			return;
		}

		try {
			monitor.beginTask(UIText.CommitMessageViewer_BuildDiffListTaskName,
					currentDiffs.size());
			BufferedOutputStream bos = new SafeBufferedOutputStream(
					new ByteArrayOutputStream() {
						@Override
						public synchronized void write(byte[] b, int off,
								int len) {
							super.write(b, off, len);
							try {
								if (currentEncoding[0] == null)
								else
									d.append(toString(currentEncoding[0]));
							} catch (UnsupportedEncodingException e) {
								d.append(toString());
							}
							reset();
						}

					});
			final DiffFormatter diffFmt = new MessageViewerFormatter(bos,
					styles, d, hunkheaderColor, linesAddedColor, linesRemovedColor);

			for (FileDiff currentDiff : currentDiffs) {
				if (monitor.isCanceled())
					throw new OperationCanceledException();
				if (currentDiff.getBlobs().length == 2) {
					String path = currentDiff.getNewPath();
					monitor.setTaskName(NLS.bind(
							UIText.CommitMessageViewer_BuildDiffTaskName, path));
					currentEncoding[0] = CompareCoreUtils.getResourceEncoding(db,
							path);
					d.append(LF);
					int start = d.length();
					String pathLine = formatPathLine(path);
					int len = pathLine.length();
					d.append(pathLine).append(LF);
					styles.add(new StyleRange(start, len, darkGrey, null));
					currentDiff.outputDiff(d, db, diffFmt, true);
					diffFmt.flush();
				}
				monitor.worked(1);
			}

		} finally {
			monitor.done();
			if (trace)
				GitTraceLocation.getTrace().traceExit(
						GitTraceLocation.HISTORYVIEW.getLocation());
		}
	}

	private String formatPathLine(String path) {
		int n = 80 - path.length() - 2;
		if (n < 0)
			return path;
		final StringBuilder d = new StringBuilder();
		int i = 0;
		for (; i < n / 2; i++)
		d.append(SPACE).append(path).append(SPACE);
		for (; i < n - 1; i++)
		return d.toString();
	}

