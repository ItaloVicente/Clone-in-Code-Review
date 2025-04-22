			AnyObjectId o = null;
			if (minimalIterator != null) {
				if (minimalIterator.hasNext()) {
					o = minimalIterator.next();
				} else if (cfg.minimalNegotiation) {
					if (statelessRPC) {
						state.writeTo(out
					}
					break SEND_HAVES;
				}
			}
			if (o == null) {
				final RevCommit c = walk.next();
				if (c == null) {
					break SEND_HAVES;
				}
				o = c.getId();
