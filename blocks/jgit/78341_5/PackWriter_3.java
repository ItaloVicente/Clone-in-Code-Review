			}
			for (RevObject obj : haveObjs) {
				if (obj instanceof RevCommit) {
					RevTree t = ((RevCommit) obj).getTree();
					depthWalk.markUninteresting(t);
				}
			}

