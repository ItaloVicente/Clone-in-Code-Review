			todoWriter.close();
		}

		if (poppedLines.size() > 0) {
			BufferedWriter doneWriter = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(doneFile
			try {
				for (String writeLine : poppedLines) {
					doneWriter.write(writeLine);
					doneWriter.newLine();
				}
			} finally {
				doneWriter.close();
			}
