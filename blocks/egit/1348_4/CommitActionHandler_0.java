			BufferedReader br = new BufferedReader(reader);
			try {
				StringBuffer message = new StringBuffer();
				String s;
				String newLine = newLine();
				while ((s = br.readLine()) != null) {
					message.append(s).append(newLine);
				}
				return message.toString();
			} catch (IOException e) {
				MessageDialog.openError(getShell(event),
						UIText.CommitAction_MergeHeadErrorTitle,
						UIText.CommitAction_ErrorReadingMergeMsg);
				throw new IllegalStateException(e);
			} finally {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
