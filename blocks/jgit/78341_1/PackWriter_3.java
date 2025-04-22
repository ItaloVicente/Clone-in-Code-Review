			}
			for (RevObject obj : haveObjs) {
				if (obj instanceof RevCommit) {
					RevTree t = ((RevCommit) obj).getTree();
					if (t != null) {
						depthWalk.markUninteresting(t);
					}
				}
			}

