		for (String r : removed) {
			File file = new File(repo.getWorkTree(), r);
			if (!file.delete())
				throw new CheckoutConflictException(
						MessageFormat.format(JGitText.get().cannotDeleteFile,
								file.getAbsolutePath()));
			removeEmptyParents(file);
		}
