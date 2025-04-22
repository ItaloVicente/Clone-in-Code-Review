	private void outputDiff(final StringBuilder d, FileDiff fileDiff) throws IOException {
		String path = fileDiff.path;
		ObjectId id1 = fileDiff.blobs[0];
		ObjectId id2 = fileDiff.blobs[1];
		FileMode mode1 = fileDiff.modes[0];
		FileMode mode2 = fileDiff.modes[1];

		if (id1.equals(ObjectId.zeroId())) {
		} else if (id2.equals(ObjectId.zeroId())) {
		} else if (!mode1.equals(mode2)) {
			d.append("old mode " + mode1);
		}
		d.append("index ").append(id1.abbreviate(db, 7).name()).
			append("..").append(id2.abbreviate(db, 7).name()). //$NON-NLS-1$

		RawText a = getRawText(id1);
		RawText b = getRawText(id2);
		MyersDiff diff = new MyersDiff(a, b);
		diffFmt.formatEdits(new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				d.append((char) b);

			}
		 } , a, b, diff.getEdits());
	}
