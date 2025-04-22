	private void processShallow() throws IOException {
		DepthWalk.RevWalk depthWalk =
			new DepthWalk.RevWalk(walk.getObjectReader()

		for (RevObject o : wantAll) {
			if (o instanceof RevCommit)
				depthWalk.markStart(depthWalk.parseCommit(o));
		}

		RevObject o;
		while ((o = depthWalk.next()) != null) {
			DepthWalk.Commit c = (DepthWalk.Commit)o;

			if (c.getDepth() == depth && !clientShallowCommits.contains(c))
				pckOut.writeString("shallow " + o.name());

			if (c.getDepth() < depth && clientShallowCommits.contains(c)) {
				unshallowCommits.add(c.copy());
				pckOut.writeString("unshallow " + c.name());
			}
		}

		pckOut.end();
	}

