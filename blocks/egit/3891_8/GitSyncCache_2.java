			if (gsd.getCommonAncestorRev() != null)
				tw.addTree(gsd.getCommonAncestorRev().getTree());
			else
				tw.addTree(new EmptyTreeIterator());

			if (gsd.getDstRevCommit() != null)
				tw.addTree(gsd.getDstRevCommit().getTree());
			else
				tw.addTree(new EmptyTreeIterator());

			List<ThreeWayDiffEntry> diffEntrys = ThreeWayDiffEntry.scan(tw);
