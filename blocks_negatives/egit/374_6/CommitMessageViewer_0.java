	private void outputDiff(final StringBuilder d, FileDiff fileDiff) throws IOException {
		String path = fileDiff.path;
		ObjectId id1 = fileDiff.blobs[0];
		ObjectId id2 = fileDiff.blobs[1];
		FileMode mode1 = fileDiff.modes[0];
		FileMode mode2 = fileDiff.modes[1];

		d.append(formatPathLine(path)).append(LF);
		if (id1.equals(ObjectId.zeroId())) {
			d.append(UIText.CommitMessageViewer_newFileMode
					+ SPACE
					+ mode2).append(LF);
		} else if (id2.equals(ObjectId.zeroId())) {
			d.append(UIText.CommitMessageViewer_deletedFileMode + SPACE + mode1).append(LF);
		} else if (!mode1.equals(mode2)) {
			d.append(UIText.CommitMessageViewer_oldMode + SPACE + mode1);
			d.append(UIText.CommitMessageViewer_newMode + SPACE + mode2).append(LF);
		}
		d.append(UIText.CommitMessageViewer_index).append(SPACE).append(id1.abbreviate(db, 7).name()).
			append("..").append(id2.abbreviate(db, 7).name()). //$NON-NLS-1$
			append (mode1.equals(mode2) ? SPACE + mode1 : EMPTY). append(LF);

		RawText a = getRawText(id1);
		RawText b = getRawText(id2);
		MyersDiff diff = new MyersDiff(a, b);
		diffFmt.formatEdits(new OutputStream() {

			@Override
			public void write(int b) throws IOException {
				d.append((char) b);

			}
		 } , a, b, diff.getEdits());
		d.append(LF);
	}

