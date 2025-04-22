		String projectRelativePath = getProjectRelativePath(db, getPath());
		d.append("diff --git ").append(projectRelativePath).append(" ") //$NON-NLS-1$ //$NON-NLS-2$
			.append(projectRelativePath).append("\n"); //$NON-NLS-1$
		final ObjectId id1 = getBlobs()[0];
		final ObjectId id2 = getBlobs()[1];
		final FileMode mode1 = getModes()[0];
		final FileMode mode2 = getModes()[1];
