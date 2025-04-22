		reader = db.newObjectReader();
		try (BlameGenerator generator = new BlameGenerator(db, file)) {
			generator.setTextComparator(comparator);

			if (!reverseRange.isEmpty()) {
				RevCommit rangeStart = null;
				List<RevCommit> rangeEnd = new ArrayList<>(2);
				for (RevCommit c : reverseRange) {
					if (c.has(RevFlag.UNINTERESTING))
						rangeStart = c;
					else
						rangeEnd.add(c);
				}
				generator.reverse(rangeStart, rangeEnd);
			} else if (revision != null) {
				generator.push(null, db.resolve(revision + "^{commit}")); //$NON-NLS-1$
			} else {
				generator.push(null, db.resolve(Constants.HEAD));
				if (!db.isBare()) {
					DirCache dc = db.readDirCache();
					int entry = dc.findEntry(file);
					if (0 <= entry)
						generator.push(null, dc.getEntry(entry).getObjectId());

					File inTree = new File(db.getWorkTree(), file);
					if (db.getFS().isFile(inTree))
						generator.push(null, new RawText(inTree));
