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
