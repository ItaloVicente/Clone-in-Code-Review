	}

	ArrayList getRejectedFiles(IStatus multiStatus, IFile[] files) {
		ArrayList filteredFiles = new ArrayList();

		IStatus[] status = multiStatus.getChildren();
		for (int i = 0; i < status.length; i++) {
			if (status[i].isOK() == false) {
				errorTable.add(status[i]);
				filteredFiles.add(files[i].getFullPath());
			}
		}
		return filteredFiles;
	}

	public IStatus getStatus() {
		IStatus[] errors = new IStatus[errorTable.size()];
		errorTable.toArray(errors);
		return new MultiStatus(PlatformUI.PLUGIN_ID, IStatus.OK, errors,
				DataTransferMessages.ImportOperation_importProblems,
				null);
	}

