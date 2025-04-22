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
				}
			}
