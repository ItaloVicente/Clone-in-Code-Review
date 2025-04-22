	private String getMergeResolveMessage(Repository mergeRepository,
			ExecutionEvent event) throws ExecutionException {
		File mergeMsg = new File(mergeRepository.getDirectory(), Constants.MERGE_MSG);
		FileReader reader;
		try {
			reader = new FileReader(mergeMsg);
		} catch (FileNotFoundException e) {
			MessageDialog.openError(getShell(event),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_MergeHeadErrorMessage);
			throw new IllegalStateException(e);
		}
		BufferedReader br = new BufferedReader(reader);
		StringBuffer message = new StringBuffer();
		String s;
		String newLine = newLine();
		try {
			while ((s = br.readLine()) != null) {
				message.append(s).append(newLine);
			}
		} catch (IOException e) {
			MessageDialog.openError(getShell(event),
					UIText.CommitAction_MergeHeadErrorTitle,
					UIText.CommitAction_ErrorReadingMergeMsg);
			throw new IllegalStateException(e);
		}
		return message.toString();
	}

	private String newLine(){
		if(System.getProperty("os.name").indexOf("Windows") != -1){ //$NON-NLS-1$ //$NON-NLS-2$
			return "\r\n"; //$NON-NLS-1$
		}
		else {
			return "\n"; //$NON-NLS-1$
		}
	}

