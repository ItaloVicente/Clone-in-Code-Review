		if (listName != null) {
			addByObjectList(listName, countingMonitor, walker,
					interestingObjects);
		} else {
			while ((o = walker.nextObject()) != null) {
				addResultOrBase(o, walker.getPathHashCode());
				countingMonitor.update(1);
			}
		}
		countingMonitor.endTask();
	}

	private void addByObjectList(RevObject listName,
			final ProgressMonitor countingMonitor, final ObjectWalk walker,
			final Collection<? extends ObjectId> interestingObjects)
			throws MissingObjectException, IncorrectObjectTypeException,
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
			addResult(o, 0);
			o.add(added);
			if (restartedProgress != 0)
				restartedProgress--;
			else
				countingMonitor.update(1);
		}
