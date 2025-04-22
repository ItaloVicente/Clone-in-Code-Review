			result.treeId = dirCache.writeTree(inserter);
			result.paths = modifiedPaths.stream().sorted()
					.collect(Collectors.toList());
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(
					JGitText.get().patchApplyException, e.getMessage()), e);
