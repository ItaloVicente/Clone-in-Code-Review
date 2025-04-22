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
