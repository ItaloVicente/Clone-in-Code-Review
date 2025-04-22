		}

		try {
			monitor.beginTask(UIText.CommitMessageViewer_BuildDiffListTaskName,
					currentDiffs.size());
			BufferedOutputStream bos = new BufferedOutputStream(
					new ByteArrayOutputStream() {
						@Override
						public synchronized void write(byte[] b, int off,
								int len) {
							super.write(b, off, len);
							if (currentEncoding[0] == null)
								d.append(toString());
							else
								try {
									d.append(toString(currentEncoding[0]));
								} catch (UnsupportedEncodingException e) {
									d.append(toString());
								}
							reset();
						}

					});
			final DiffFormatter diffFmt = new MessageViewerFormatter(bos,
					styles, d);

			for (FileDiff currentDiff : currentDiffs) {
				if (monitor.isCanceled())
					throw new InterruptedException();
				if (currentDiff.getBlobs().length == 2) {
					String path = currentDiff.getPath();
					monitor
							.setTaskName(NLS
									.bind(
											UIText.CommitMessageViewer_BuildDiffTaskName,
											path));
					currentEncoding[0] = CompareUtils.getResourceEncoding(db,
							path);
					d.append(formatPathLine(path)).append(LF);
					currentDiff.outputDiff(d, db, diffFmt, true);
					diffFmt.flush();
				}
				monitor.worked(1);
			}

		} catch (IOException e) {
			throw new InvocationTargetException(e);
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

	private static final class MessageViewerFormatter extends DiffFormatter {
		private final List<StyleRange> styles;

		private final StringBuilder d;

		private MessageViewerFormatter(OutputStream out,
				List<StyleRange> styles, StringBuilder d) {
			super(out);
			this.styles = styles;
			this.d = d;
		}

		@Override
		protected void writeHunkHeader(int aCur, int aEnd, int bCur, int bEnd)
				throws IOException {
			flush();
			int start = d.length();
			super.writeHunkHeader(aCur, aEnd, bCur, bEnd);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start, SYS_HUNKHEADER_COLOR,
					null));
		}

		@Override
		protected void writeAddedLine(RawText b, int bCur) throws IOException {
			flush();
			int start = d.length();
			super.writeAddedLine(b, bCur);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start,
					SYS_LINES_ADDED_COLOR, null));
		}

		@Override
		protected void writeRemovedLine(RawText b, int bCur) throws IOException {
			flush();
			int start = d.length();
			super.writeRemovedLine(b, bCur);
			flush();
			int end = d.length();
			styles.add(new StyleRange(start, end - start,
					SYS_LINES_REMOVED_COLOR, null));
		}
