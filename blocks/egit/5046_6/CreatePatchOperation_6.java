	public void updateWorkspacePatchPrefixes(StringBuilder sb, DiffFormatter diffFmt) {
		RawText rt = new RawText(sb.toString().getBytes());

		final String oldPrefix = diffFmt.getOldPrefix();
		final String newPrefix = diffFmt.getNewPrefix();

		StringBuilder newSb = new StringBuilder();
		final Pattern diffPattern = Pattern
				.compile("^diff --git (" + oldPrefix + "(.+)) (" + newPrefix + "(.+))$"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		final Pattern oldPattern = Pattern
				.compile("^--- (" + oldPrefix + "(.+))$"); //$NON-NLS-1$ //$NON-NLS-2$
		final Pattern newPattern = Pattern
				.compile("^\\+\\+\\+ (" + newPrefix + "(.+))$"); //$NON-NLS-1$ //$NON-NLS-2$

		int i = 0;
		while (i < rt.size()) {
			String line = rt.getString(i);

			Matcher diffMatcher = diffPattern.matcher(line);
			Matcher oldMatcher = oldPattern.matcher(line);
			Matcher newMatcher = newPattern.matcher(line);
			if (diffMatcher.find()) {
				String group = diffMatcher.group(2); // old path
				IProject project = getProject(group);
				IPath newPath = computeWorkspacePath(new Path(group), project);
				line = line.replaceAll(diffMatcher.group(1), newPath.toString());
				group = diffMatcher.group(4); // new path
				newPath = computeWorkspacePath(new Path(group), project);
				line = line.replaceAll(diffMatcher.group(3), newPath.toString());
			} else if (oldMatcher.find()) {
				String group = oldMatcher.group(2);
				IProject project = getProject(group);
				IPath newPath = computeWorkspacePath(new Path(group), project);
				line = line.replaceAll(oldMatcher.group(1), newPath.toString());
			} else if (newMatcher.find()) {
				String group = newMatcher.group(2);
				IProject project = getProject(group);
				IPath newPath = computeWorkspacePath(new Path(group), project);
				line = line.replaceAll(newMatcher.group(1), newPath.toString());
			}
			newSb.append(line);

			i++;
			if (i < rt.size() || !rt.isMissingNewlineAtEnd())
				newSb.append(rt.getLineDelimiter());
		}
		sb.setLength(0);
		sb.append(newSb);
	}

	public static IPath computeWorkspacePath(final IPath path, final IProject project) {
		RepositoryMapping rm = RepositoryMapping.getMapping(project);
		String repoRelativePath = rm.getRepoRelativePath(project);
		if (repoRelativePath == null)
			return path;
		if (repoRelativePath.equals("")) //$NON-NLS-1$
			return path;
		return path.removeFirstSegments(path.matchingFirstSegments(new Path(
				repoRelativePath)));
	}


