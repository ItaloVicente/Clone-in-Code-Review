		}
	}

	private void processShallow() throws IOException {
		DepthWalk.RevWalk depthWalk = new DepthWalk.RevWalk(db

		for (RevCommit c : wantCommits) {
			RevCommit commit = depthWalk.parseCommit(c);
			depthWalk.markStart(commit);
		}

		RevObject o;

		while ((o = depthWalk.next()) != null) {
			localShallowCommits.add((RevCommit)o);

			boolean found = false;
			for (RevCommit c : remoteShallowCommits) {
				if (c.name() == o.name()) {
					found = true;
					break;
				}
			}

			if (!found) {
				pckOut.writeString("shallow " + o.name());
			}
		}

		for (RevCommit remote : remoteShallowCommits) {
			boolean found = false;
			for(RevCommit local : localShallowCommits) {
				if(remote.name() == local.name()) {
					found = true;
					break;
				}
			}

			if (!found) {
				pckOut.writeString("unshallow " + remote.name());
			}
		}

		pckOut.end();
