		if (checkReferencedIsReachable) {
			baseObjects = parser.getBaseObjectIds();
			providedObjects = parser.getNewObjectIds();
		}
		parser = null;

		try {
			Set<ObjectId> immediateRefs = new HashSet<>();
			for (ReceiveCommand cmd : commands) {
				if (cmd.getType() == ReceiveCommand.Type.UPDATE || cmd
						.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD) {
					if (advertisedHaves.contains(cmd.getOldId())) {
						immediateRefs.add(cmd.getOldId());
					}
				}
			}
			checkConnectivity(baseObjects, providedObjects, immediateRefs,
					checking);
			return;
		} catch (MissingObjectException e) {
		}
		checkConnectivity(baseObjects, providedObjects, advertisedHaves,
				checking);

	}

	private void checkConnectivity(ObjectIdSubclassMap<ObjectId> baseObjects,
			ObjectIdSubclassMap<ObjectId> providedObjects, Set<ObjectId> haves,
			ProgressMonitor checking)
			throws MissingObjectException, IOException {
		try (ObjectWalk ow = new ObjectWalk(db)) {
			if (baseObjects != null) {
				ow.sort(RevSort.TOPO);
				if (!baseObjects.isEmpty())
					ow.sort(RevSort.BOUNDARY, true);
			}

			for (ReceiveCommand cmd : commands) {
				if (cmd.getResult() != Result.NOT_ATTEMPTED)
					continue;
				if (cmd.getType() == ReceiveCommand.Type.DELETE)
					continue;
				ow.markStart(ow.parseAny(cmd.getNewId()));
			}
			for (ObjectId have : haves) {
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

			checking.beginTask(JGitText.get().countingObjects,
					ProgressMonitor.UNKNOWN);
			RevCommit c;
			while ((c = ow.next()) != null) {
				checking.update(1);
						&& !providedObjects.contains(c))
					throw new MissingObjectException(c, Constants.TYPE_COMMIT);
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
						throw new MissingObjectException(o, o.getType());
				}

				if (o instanceof RevBlob && !db.getObjectDatabase().has(o))
					throw new MissingObjectException(o, Constants.TYPE_BLOB);
			}
			checking.endTask();

			if (baseObjects != null) {
				for (ObjectId id : baseObjects) {
					o = ow.parseAny(id);
					if (!o.has(RevFlag.UNINTERESTING))
						throw new MissingObjectException(o, o.getType());
				}
			}
		}
