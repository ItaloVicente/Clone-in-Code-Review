		List<String> todoLines = new ArrayList<String>();
		List<String> poppedLines = new ArrayList<String>();
		File todoFile = rebaseState.getFile(GIT_REBASE_TODO);
		File doneFile = rebaseState.getFile(DONE);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(todoFile), Constants.CHARACTER_ENCODING));
		try {
			while (poppedLines.size() < numSteps) {
				String popCandidate = br.readLine();
				if (popCandidate == null)
					break;
				if (popCandidate.length() == 0)
					continue;
				if (popCandidate.charAt(0) == '#')
					continue;
				int spaceIndex = popCandidate.indexOf(' ');
				boolean pop = false;
				if (spaceIndex >= 0) {
					String actionToken = popCandidate.substring(0, spaceIndex);
					pop = Action.parse(actionToken) != null;
				}
				if (pop)
					poppedLines.add(popCandidate);
				else
					todoLines.add(popCandidate);
			}
			String readLine = br.readLine();
			while (readLine != null) {
				todoLines.add(readLine);
				readLine = br.readLine();
			}
		} finally {
			br.close();
		}

		BufferedWriter todoWriter = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(todoFile), Constants.CHARACTER_ENCODING));
		try {
			for (String writeLine : todoLines) {
				todoWriter.write(writeLine);
				todoWriter.newLine();
			}
		} finally {
			todoWriter.close();
