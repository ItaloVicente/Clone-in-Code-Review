			reader = new FileReader(mergeMsg);
			BufferedReader br = new BufferedReader(reader);
			try {
				StringBuilder message = new StringBuilder();
				String s;
				String newLine = newLine();
				while ((s = br.readLine()) != null)
					message.append(s).append(newLine);
				return message.toString();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			} finally {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		} catch (FileNotFoundException e) {
			return NLS.bind(UIText.CommitHelper_couldNotFindMergeMsg, Constants.MERGE_MSG);
