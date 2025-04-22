		diffFmt.formatEdits(new OutputStream() {

			@Override
			public void write(int c) throws IOException {
				d.append((char) c);

			}
		}, a, b, diff.getEdits());
