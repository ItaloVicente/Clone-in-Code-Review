		if (checkReferencedIsReachable) {
			baseObjects = parser.getBaseObjectIds();
			providedObjects = parser.getNewObjectIds();
		}
		parser = null;

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
