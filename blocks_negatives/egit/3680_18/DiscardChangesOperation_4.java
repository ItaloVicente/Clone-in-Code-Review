		for (IResource res : files) {
			Repository repo = getRepository(res);
			if (repo == null) {
				IStatus status = Activator.error(
						CoreText.DiscardChangesOperation_repoNotFound, null);
				throw new CoreException(status);
			}
		}
