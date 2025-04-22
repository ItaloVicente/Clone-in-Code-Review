		try (Writer fw = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(f)
			for (Iterator<String> l = newLines.iterator(); l.hasNext();) {
				fw.write(l.next());
				if (l.hasNext()) {
					fw.write('\n');
				}
			}
