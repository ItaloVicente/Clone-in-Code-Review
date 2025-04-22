
			for (int i = 0; i < cmit.getParentCount(); i++) {
				RevCommit p = cmit.getParent(i);
				if (!p.has(added) && !p.has(RevFlag.UNINTERESTING)) {
					p.add(added);
					addObject(p, 0);
