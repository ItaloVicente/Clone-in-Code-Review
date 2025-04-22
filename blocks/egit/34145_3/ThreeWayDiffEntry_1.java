					&& e.baseId.equals(e.remoteId)) {
				if (forceInCache.contains(walk.getPathString())) {
					e.direction = Direction.INCOMING;
					e.changeType = ChangeType.IN_SYNC;
					e.path = walk.getPathString();
					r.add(e);
				}
