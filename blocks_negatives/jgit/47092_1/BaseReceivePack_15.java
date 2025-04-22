		final ObjectWalk ow = new ObjectWalk(db);
		ow.setRetainBody(false);
		if (baseObjects != null) {
			ow.sort(RevSort.TOPO);
			if (!baseObjects.isEmpty())
				ow.sort(RevSort.BOUNDARY, true);
		}

		for (final ReceiveCommand cmd : commands) {
			if (cmd.getResult() != Result.NOT_ATTEMPTED)
				continue;
			if (cmd.getType() == ReceiveCommand.Type.DELETE)
				continue;
			ow.markStart(ow.parseAny(cmd.getNewId()));
		}
		for (final ObjectId have : advertisedHaves) {
			RevObject o = ow.parseAny(have);
			ow.markUninteresting(o);

			if (baseObjects != null && !baseObjects.isEmpty()) {
				o = ow.peel(o);
				if (o instanceof RevCommit)
					o = ((RevCommit) o).getTree();
				if (o instanceof RevTree)
					ow.markUninteresting(o);
