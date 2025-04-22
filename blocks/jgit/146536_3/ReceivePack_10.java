	private void receivePack() throws IOException {
		if (timeoutIn != null)
			timeoutIn.setTimeout(10 * timeout * 1000);

		ProgressMonitor receiving = NullProgressMonitor.INSTANCE;
		ProgressMonitor resolving = NullProgressMonitor.INSTANCE;
		if (sideBand && !quiet)
			resolving = new SideBandProgressMonitor(msgOut);

		try (ObjectInserter ins = db.newObjectInserter()) {
			if (getRefLogIdent() != null)

			parser = ins.newPackParser(packInputStream());
			parser.setAllowThin(true);
			parser.setNeedNewObjectIds(checkReferencedIsReachable);
			parser.setNeedBaseObjectIds(checkReferencedIsReachable);
			parser.setCheckEofAfterPackFooter(
					!biDirectionalPipe && !isExpectDataAfterPackFooter());
			parser.setExpectDataAfterPackFooter(isExpectDataAfterPackFooter());
			parser.setObjectChecker(objectChecker);
			parser.setLockMessage(lockMsg);
			parser.setMaxObjectSizeLimit(maxObjectSizeLimit);
			packLock = parser.parse(receiving
			packSize = Long.valueOf(parser.getPackSize());
			stats = parser.getReceivedPackStatistics();
			ins.flush();
		}

		if (timeoutIn != null)
			timeoutIn.setTimeout(timeout * 1000);
	}

	private InputStream packInputStream() {
		InputStream packIn = rawIn;
		if (maxPackSizeLimit >= 0) {
			packIn = new LimitedInputStream(packIn
				@Override
				protected void limitExceeded() throws TooLargePackException {
					throw new TooLargePackException(limit);
				}
			};
		}
		return packIn;
	}

	private boolean needCheckConnectivity() {
		return isCheckReceivedObjects()
				|| isCheckReferencedObjectsAreReachable()
				|| !getClientShallowCommits().isEmpty();
	}

	private void checkSubmodules() throws IOException {
		ObjectDatabase odb = db.getObjectDatabase();
		if (objectChecker == null) {
			return;
		}
		for (GitmoduleEntry entry : objectChecker.getGitsubmodules()) {
			AnyObjectId blobId = entry.getBlobId();
			ObjectLoader blob = odb.open(blobId

			try {
				SubmoduleValidator.assertValidGitModulesFile(
						new String(blob.getBytes()
			} catch (LargeObjectException | SubmoduleValidationException e) {
				throw new IOException(e);
			}
		}
	}

	private void checkConnectivity() throws IOException {
		ObjectIdSubclassMap<ObjectId> baseObjects = null;
		ObjectIdSubclassMap<ObjectId> providedObjects = null;
		ProgressMonitor checking = NullProgressMonitor.INSTANCE;
		if (sideBand && !quiet) {
			SideBandProgressMonitor m = new SideBandProgressMonitor(msgOut);
			m.setDelayStart(750
			checking = m;
		}

		if (checkReferencedIsReachable) {
			baseObjects = parser.getBaseObjectIds();
			providedObjects = parser.getNewObjectIds();
		}
		parser = null;

		try (ObjectWalk ow = new ObjectWalk(db)) {
			if (baseObjects != null) {
				ow.sort(RevSort.TOPO);
				if (!baseObjects.isEmpty())
					ow.sort(RevSort.BOUNDARY
			}

			for (ReceiveCommand cmd : commands) {
				if (cmd.getResult() != Result.NOT_ATTEMPTED)
					continue;
				if (cmd.getType() == ReceiveCommand.Type.DELETE)
					continue;
				ow.markStart(ow.parseAny(cmd.getNewId()));
			}
			for (ObjectId have : advertisedHaves) {
				RevObject o = ow.parseAny(have);
				ow.markUninteresting(o);

				if (baseObjects != null && !baseObjects.isEmpty()) {
					o = ow.peel(o);
					if (o instanceof RevCommit)
						o = ((RevCommit) o).getTree();
					if (o instanceof RevTree)
						ow.markUninteresting(o);
				}
			}

			checking.beginTask(JGitText.get().countingObjects
					ProgressMonitor.UNKNOWN);
			RevCommit c;
			while ((c = ow.next()) != null) {
				checking.update(1);
						&& !providedObjects.contains(c))
					throw new MissingObjectException(c
			}

			RevObject o;
			while ((o = ow.nextObject()) != null) {
				checking.update(1);
				if (o.has(RevFlag.UNINTERESTING))
					continue;

				if (providedObjects != null) {
					if (providedObjects.contains(o))
						continue;
					else
						throw new MissingObjectException(o
				}

				if (o instanceof RevBlob && !db.getObjectDatabase().has(o))
					throw new MissingObjectException(o
			}
			checking.endTask();

			if (baseObjects != null) {
				for (ObjectId id : baseObjects) {
					o = ow.parseAny(id);
					if (!o.has(RevFlag.UNINTERESTING))
						throw new MissingObjectException(o
				}
			}
		}
