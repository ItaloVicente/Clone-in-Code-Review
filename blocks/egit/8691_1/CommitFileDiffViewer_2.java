
		IFile file = getFileForLocation(getRepository(), p);
		if (file != null && leftCommit != null && rightCommit != null) {
			if(!isOKToShowSingleFile(file)) {
				try {
					synchronizeModelBetweenRefs(file, getRepository(),
							leftCommit.getName(), rightCommit.getName());
				} catch (Exception e) {
					Activator.logError(UIText.GitHistoryPage_openFailed, e);
					Activator.showError(UIText.GitHistoryPage_openFailed, null);
				}
				return;
			}
		}

		final ITypedElement base= createTypedElement(p, leftCommit, baseObjectId);
		final ITypedElement next= createTypedElement(p, rightCommit, rightObjectId);
