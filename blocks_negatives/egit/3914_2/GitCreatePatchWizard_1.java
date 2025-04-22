				public void run(IProgressMonitor monitor) {
					final StringBuilder sb = new StringBuilder();
					final DiffFormatter diffFmt = new DiffFormatter(
							new BufferedOutputStream(new ByteArrayOutputStream() {

						@Override
						public synchronized void write(byte[] b, int off, int len) {
							super.write(b, off, len);
							if (currentEncoding == null)
								sb.append(toString());
							else try {
								sb.append(toString(currentEncoding));
							} catch (UnsupportedEncodingException e) {
								sb.append(toString());
							}
							reset();
						}

					}));

					if (isGit)
						writeGitPatchHeader(sb);
