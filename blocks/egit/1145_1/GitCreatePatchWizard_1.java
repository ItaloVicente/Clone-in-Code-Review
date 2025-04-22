					final StringBuilder sb = new StringBuilder();
					DiffFormatter diffFmt = new DiffFormatter(new OutputStream() {

						@Override
						public void write(int c) throws IOException {
							sb.append((char) c);

						}
					});
