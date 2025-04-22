	private void addDiff(final StringBuilder d) {
		if (!(commit.getParentCount() == 1))
			return;
		try {
			FileDiff[] diffs = FileDiff.compute(walker, commit);

			for (FileDiff diff : diffs) {
				if (diff.blobs.length == 2)
					outputDiff(d, diff);
			}
		} catch (IOException e) {
			Activator.error("Can't get file difference of "
					+ commit.getId() + ".", e);
		}
	}

	private void outputDiff(final StringBuilder d, FileDiff fileDiff) throws IOException {
		String path = fileDiff.path;
		ObjectId id1 = fileDiff.blobs[0];
		ObjectId id2 = fileDiff.blobs[1];
		FileMode mode1 = fileDiff.modes[0];
		FileMode mode2 = fileDiff.modes[1];

		d.append(formatPathLine(path)).append("\n"); //$NON-NLS-1$
		if (id1.equals(ObjectId.zeroId())) {
			d.append("new file mode " + mode2).append("\n"); //$NON-NLS-2$
		} else if (id2.equals(ObjectId.zeroId())) {
			d.append("deleted file mode " + mode1).append("\n"); //$NON-NLS-2$
		} else if (!mode1.equals(mode2)) {
			d.append("old mode " + mode1);
			d.append("new mode " + mode2).append("\n"); //$NON-NLS-2$
		}
		d.append("index ").append(id1.abbreviate(db, 7).name()).
			append("..").append(id2.abbreviate(db, 7).name()). //$NON-NLS-1$
			append (mode1.equals(mode2) ? " " + mode1 : ""). append("\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		RawText a = getRawText(id1);
		RawText b = getRawText(id2);
		MyersDiff diff = new MyersDiff(a, b);
		diffFmt.formatEdits(new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				d.append((char) b);

			}
		 } , a, b, diff.getEdits());
		d.append("\n"); //$NON-NLS-1$
	}

	private String formatPathLine(String path) {
		int n = 80 - path.length() - 2;
		if (n < 0 )
			return path;
		final StringBuilder d = new StringBuilder();
		int i = 0;
		for (; i < n/2; i++)
			d.append("-"); //$NON-NLS-1$
		d.append(" ").append(path).append(" "); //$NON-NLS-1$ //$NON-NLS-2$
		for (; i < n - 1; i++)
			d.append("-"); //$NON-NLS-1$
		return d.toString();
	}


	private RawText getRawText(ObjectId id) throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] { });
		return new RawText(db.openBlob(id).getCachedBytes());
	}

