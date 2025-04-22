	private void addByObjectList(RevObject listName
			final ProgressMonitor countingMonitor
			final Collection<? extends ObjectId> interestingObjects)
			throws MissingObjectException
			IOException {
		int restartedProgress = objectsMap.size();

		for (List<ObjectToPack> list : objectsLists)
			list.clear();
		objectsMap.clear();

		walker.reset();
		walker.markUninteresting(listName);

		for (ObjectId id : interestingObjects)
			walker.markStart(walker.lookupOrNull(id));

		RevFlag added = walker.newFlag("added");
		RevObject o;
		while ((o = walker.next()) != null) {
			addResult(o
			o.add(added);
			if (restartedProgress != 0)
				restartedProgress--;
			else
				countingMonitor.update(1);
		}
		while ((o = walker.nextObject()) != null) {
			addResult(o
			o.add(added);
			if (restartedProgress != 0)
				restartedProgress--;
			else
				countingMonitor.update(1);
		}

		ObjectListIterator itr = reader.openObjectList(listName
		try {
			while ((o = itr.next()) != null) {
				if (o.has(added))
					continue;
				addResult(o
				o.add(added);
				if (restartedProgress != 0)
					restartedProgress--;
				else
					countingMonitor.update(1);
			}
			while ((o = itr.nextObject()) != null) {
				if (o.has(added))
					continue;
				addResult(o
				o.add(added);
				if (restartedProgress != 0)
					restartedProgress--;
				else
					countingMonitor.update(1);
			}
		} finally {
			itr.release();
		}
	}

