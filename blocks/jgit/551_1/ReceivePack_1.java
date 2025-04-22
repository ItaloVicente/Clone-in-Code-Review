		for (final Ref ref : refs.values()) {
			RevObject o = ow.parseAny(ref.getObjectId());
			ow.markUninteresting(o);

			if (ensureObjectsProvidedVisible && !baseObjects.isEmpty()) {
				while (o instanceof RevTag)
					o = ((RevTag) o).getObject();
				if (o instanceof RevCommit)
					o = ((RevCommit) o).getTree();
				if (o instanceof RevTree)
					ow.markUninteresting(o);
			}
		}
