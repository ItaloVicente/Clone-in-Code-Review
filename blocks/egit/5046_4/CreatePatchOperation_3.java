	public static void updateWorkspacePatchPrefixes(StringBuilder sb, IResource resource, DiffFormatter diffFmt) {
		RawText rt = new RawText(sb.toString().getBytes());

		final String oldPrefix = diffFmt.getOldPrefix();
		final String newPrefix = diffFmt.getNewPrefix();

		StringBuilder newSb = new StringBuilder();
		final String eol = rt.getLineDelimiter();

		final Pattern diffPattern = Pattern
				.compile("^diff --git (" + oldPrefix + ".+) (" + newPrefix + ".+)$"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Pattern oldPattern = Pattern
				.compile("^--- (" + oldPrefix + ".+)$"); //$NON-NLS-1$ //$NON-NLS-2$
		final Pattern newPattern = Pattern
				.compile("^\\+\\+\\+ (" + newPrefix + ".+)$"); //$NON-NLS-1$ //$NON-NLS-2$

		int i = 0;
		while (i < rt.size()) {
			String line = rt.getString(i);

			Matcher diffMatcher = diffPattern.matcher(line);
			Matcher oldMatcher = oldPattern.matcher(line);
			Matcher newMatcher = newPattern.matcher(line);
			if (diffMatcher.find()) {
				String group = diffMatcher.group(1); // old path
				IPath newPath = processPath(new Path(group), resource.getProject(), new Path(oldPrefix));
				line = line.replaceAll(group, newPath.toString());
				group = diffMatcher.group(2); // new path
				newPath = processPath(new Path(group), resource.getProject(), new Path(newPrefix));
				line = line.replaceAll(group, newPath.toString());
			} else if (oldMatcher.find()) {
				String group = oldMatcher.group(1);
				IPath newPath = processPath(new Path(group), resource.getProject(), new Path(oldPrefix));
				line = line.replaceAll(group, newPath.toString());
			} else if (newMatcher.find()) {
				String group = newMatcher.group(1);
				IPath newPath = processPath(new Path(group), resource.getProject(), new Path(newPrefix));
				line = line.replaceAll(group, newPath.toString());
			}
			newSb.append(line);

			i++;
			if (i < rt.size() || !rt.isMissingNewlineAtEnd())
				newSb.append(eol);
		}
		sb.setLength(0);
		sb.append(newSb);
	}

	public static IPath processPath(final IPath path, final IProject project, final IPath prefix) {
		RepositoryMapping rm = RepositoryMapping.getMapping(project);
		String repoRelativePath = rm.getRepoRelativePath(project);
		if (repoRelativePath == null)
			return path;
		IPath result = path.removeFirstSegments(prefix.segmentCount());
		if (repoRelativePath.equals("")) //$NON-NLS-1$
			return result;
		return result.removeFirstSegments(result
				.matchingFirstSegments(new Path(repoRelativePath)));
	}


