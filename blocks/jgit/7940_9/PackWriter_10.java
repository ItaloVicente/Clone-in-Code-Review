	private final boolean have(ObjectToPack ptr
		return (ptr != null && ptr.isEdge())
				|| (haveObjects != null && haveObjects.contains(objectId));
	}

	public boolean prepareBitmapIndex(
			ProgressMonitor pm
			throws IOException {
		if (!canBuildBitmaps || getObjectCount() > Integer.MAX_VALUE
				|| !cachedPacks.isEmpty())
			return false;

		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;

		writeBitmaps = new PackBitmapIndexBuilder(sortByName());
		PackWriterBitmapPreparer bitmapPreparer =
				new PackWriterBitmapPreparer(reader

		int numCommits = objectsLists[Constants.OBJ_COMMIT].size();
		Collection<PackWriterBitmapPreparer.BitmapCommit> selectedCommits =
				bitmapPreparer.doCommitSelection(numCommits);

		beginPhase(PackingPhase.BUILDING_BITMAPS

		PackWriterBitmapWalker walker = bitmapPreparer.newBitmapWalker();
		AnyObjectId last = null;
		for (PackWriterBitmapPreparer.BitmapCommit cmit : selectedCommits) {
			if (cmit.isReuseWalker())
				walker.reset();
			else
				walker = bitmapPreparer.newBitmapWalker();

			BitmapBuilder bitmap = walker.findObjects(
					Collections.singleton(cmit.getObjectId())

			if (last != null && cmit.isReuseWalker() && !bitmap.contains(last))
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().bitmapMissingObject
						cmit.getObjectId().name()
			last = cmit.getObjectId();
			writeBitmaps.addBitmap(cmit.getObjectId()

			pm.update(1);
		}

		endPhase(pm);
		return true;
	}

