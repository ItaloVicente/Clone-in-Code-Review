
		@Override
		public final boolean include(RevWalk walker
			if (bitmap.add(cmit
				return true;
			}
			for (RevCommit p : cmit.getParents()) {
				p.add(RevFlag.SEEN);
