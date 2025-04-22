		if (walker instanceof DepthWalk.ObjectWalk) {
			DepthWalk.ObjectWalk depthWalk = (DepthWalk.ObjectWalk) walker;
			for (RevObject obj : wantObjs)
				depthWalk.markRoot(obj);
			if (unshallowObjects != null) {
				for (ObjectId id : unshallowObjects)
					depthWalk.markUnshallow(walker.parseAny(id));
			}
		} else {
			for (RevObject obj : wantObjs)
				walker.markStart(obj);
		}
