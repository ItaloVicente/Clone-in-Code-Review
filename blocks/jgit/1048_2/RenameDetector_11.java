		public int compare(DiffEntry a
			int cmp = nameOf(a).compareTo(nameOf(b));
			if (cmp == 0)
				cmp = sortOf(a.getChangeType()) - sortOf(b.getChangeType());
			return cmp;
		}

		private String nameOf(DiffEntry ent) {
			if (ent.changeType == ChangeType.DELETE)
				return ent.oldName;
			return ent.newName;
		}

		private int sortOf(ChangeType changeType) {
			switch (changeType) {
			case DELETE:
				return 1;
			case ADD:
				return 2;
			default:
				return 10;
			}
