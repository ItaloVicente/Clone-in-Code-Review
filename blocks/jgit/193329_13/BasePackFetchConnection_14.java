			if (transport.getDepth() == null) {
				try {
					if (walk.parseAny(objectId).has(REACHABLE)) {
						continue;
					}
				} catch (IOException err) {
