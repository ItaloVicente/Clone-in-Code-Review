			BufferedWriter doneWriter = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(doneFile, true),
							Constants.CHARACTER_ENCODING));
			try {
				for (String writeLine : poppedLines) {
					doneWriter.write(writeLine);
					doneWriter.newLine();
				}
			} finally {
				doneWriter.close();
			}
