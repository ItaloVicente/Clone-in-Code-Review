
		TemporaryBuffer buffer = new TemporaryBuffer.LocalFile(null);
		try {
			try (Writer w = new BufferedWriter(
					new OutputStreamWriter(buffer
				for (Iterator<String> l = newLines.iterator(); l.hasNext();) {
					w.write(l.next());
					if (l.hasNext()) {
						w.write('\n');
					}
