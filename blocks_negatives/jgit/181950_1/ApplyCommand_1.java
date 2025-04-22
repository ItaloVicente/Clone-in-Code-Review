		}
		if (!isChanged(oldLines, newLines)) {
		}
		try (Writer fw = Files.newBufferedWriter(f.toPath())) {
			for (Iterator<String> l = newLines.iterator(); l.hasNext();) {
				fw.write(l.next());
				if (l.hasNext()) {
					fw.write('\n');
