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
