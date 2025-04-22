	public void outputDiff(final StringBuilder d, final Repository db,
			final DiffFormatter diffFmt, boolean noPrefix,
			boolean pathRelativeToProject) throws IOException {
		if (!(blobs.length == 2))
			throw new UnsupportedOperationException(
					"Not supported yet if the number of parents is different from one"); //$NON-NLS-1$

		final ObjectId id1 = blobs[0];
		final ObjectId id2 = blobs[1];
		final FileMode mode1 = modes[0];
		final FileMode mode2 = modes[1];

		if (id1.equals(ObjectId.zeroId())) {
			d.append("new file mode " + mode2).append("\n"); //$NON-NLS-1$//$NON-NLS-2$
		} else if (id2.equals(ObjectId.zeroId())) {
			d.append("deleted file mode " + mode1).append("\n"); //$NON-NLS-1$//$NON-NLS-2$
		} else if (!mode1.equals(mode2)) {
			d.append("old mode " + mode1); //$NON-NLS-1$
			d.append("new mode " + mode2).append("\n"); //$NON-NLS-1$//$NON-NLS-2$
		}
		d.append("index ").append(id1.abbreviate(db, 7).name()). //$NON-NLS-1$
				append("..").append(id2.abbreviate(db, 7).name()). //$NON-NLS-1$
				append(mode1.equals(mode2) ? " " + mode1 : "").append("\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		if (id1.equals(ObjectId.zeroId()))
			d.append("--- /dev/null\n"); //$NON-NLS-1$
		else {
			d.append("--- "); //$NON-NLS-1$
			if (!noPrefix)
				d.append("a").append(IPath.SEPARATOR); //$NON-NLS-1$
			if (pathRelativeToProject)
				d.append(getProjectRelaticePath(db, path));
			else
				d.append(path);
			d.append("\n"); //$NON-NLS-1$
		}

		if (id2.equals(ObjectId.zeroId()))
			d.append("+++ /dev/null\n"); //$NON-NLS-1$
		else {
			d.append("+++ "); //$NON-NLS-1$
			if (!noPrefix)
				d.append("b").append(IPath.SEPARATOR); //$NON-NLS-1$
			if (pathRelativeToProject)
				d.append(getProjectRelaticePath(db, path));
			else
				d.append(path);
			d.append("\n"); //$NON-NLS-1$
		}

		final RawText a = getRawText(id1, db);
		final RawText b = getRawText(id2, db);
		final MyersDiff diff = new MyersDiff(a, b);
		diffFmt.formatEdits(new OutputStream() {

			@Override
			public void write(int c) throws IOException {
				d.append((char) c);

			}
		}, a, b, diff.getEdits());
	}

	private String getProjectRelaticePath(Repository db, String repoPath) {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IPath absolutePath = new Path(db.getWorkDir().getAbsolutePath()).append(repoPath);
		IResource resource = root.getFileForLocation(absolutePath);
		return resource.getProjectRelativePath().toString();
	}

	private RawText getRawText(ObjectId id, Repository db) throws IOException {
		if (id.equals(ObjectId.zeroId()))
			return new RawText(new byte[] { });
		return new RawText(db.openBlob(id).getCachedBytes());
	}

