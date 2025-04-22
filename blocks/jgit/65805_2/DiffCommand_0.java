		try (DiffFormatter diffFmt = newDiffFormatter();
				RevWalk rw = new RevWalk(diffFmt.getObjectReader())) {
			AbstractTreeIterator oldTreeIt = resolveOldTree(rw);
			AbstractTreeIterator newTreeIt = resolveNewTree(rw);
			List<DiffEntry> result = diffFmt.scan(oldTreeIt
			if (showNameAndStatusOnly) {
				return result;
			}
			if (contextLines >= 0)
				diffFmt.setContext(contextLines);
			if (destinationPrefix != null)
				diffFmt.setNewPrefix(destinationPrefix);
			if (sourcePrefix != null)
				diffFmt.setOldPrefix(sourcePrefix);
			diffFmt.format(result);
			diffFmt.flush();
			return result;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private DiffFormatter newDiffFormatter() {
