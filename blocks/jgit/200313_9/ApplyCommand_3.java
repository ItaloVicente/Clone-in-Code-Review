		try {
			PatchApplier patchApplier = new PatchApplier(repo);
			Result applyResult = patchApplier.applyPatch(patch);
			if (!applyResult.getErrors().isEmpty()) {
				throw new PatchApplyException(
						MessageFormat.format(JGitText.get().patchApplyException
						applyResult.getErrors()));
			}
			for (String p : applyResult.getPaths()) {
				r.addUpdatedFile(new File(repo.getWorkTree()
			}
		} catch (IOException e) {
			throw new PatchApplyException(MessageFormat.format(JGitText.get().patchApplyException
					e.getMessage()
