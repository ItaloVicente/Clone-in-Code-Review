		try (ObjectReader reader = db.newObjectReader()) {
			try (BlameGenerator generator = new BlameGenerator(db
				generator.setTextComparator(comparator);

				if (!reverseRange.isEmpty()) {
					RevCommit rangeStart = null;
					List<RevCommit> rangeEnd = new ArrayList<>(2);
					for (RevCommit c : reverseRange) {
						if (c.has(RevFlag.UNINTERESTING)) {
							rangeStart = c;
						} else {
							rangeEnd.add(c);
						}
					}
					generator.reverse(rangeStart
				} else if (revision != null) {
					generator.push(null
				} else {
					generator.push(null
					if (!db.isBare()) {
						DirCache dc = db.readDirCache();
						int entry = dc.findEntry(file);
						if (0 <= entry) {
							generator.push(null
									dc.getEntry(entry).getObjectId());
						}
						File inTree = new File(db.getWorkTree()
						if (db.getFS().isFile(inTree)) {
							generator.push(null
						}
					}
