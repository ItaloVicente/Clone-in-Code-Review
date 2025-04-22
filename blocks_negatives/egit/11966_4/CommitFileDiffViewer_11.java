
		IFile file = ResourceUtil.getFileForLocation(getRepository(), p);
		if (file != null && leftCommit != null && rightCommit != null) {
			if (!CompareUtils.canDirectlyOpenInCompare(file)) {
				try {
					GitModelSynchronize.synchronizeModelBetweenRefs(file,
							getRepository(), leftCommit.getName(),
							rightCommit.getName());
				} catch (Exception e) {
					Activator.logError(UIText.GitHistoryPage_openFailed, e);
					Activator.showError(UIText.GitHistoryPage_openFailed, null);
