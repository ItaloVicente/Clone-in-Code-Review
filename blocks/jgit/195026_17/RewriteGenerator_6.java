			p = transform(p.getParent(0));

		}
	}

	private RevCommit transform(RevCommit c) {
		if (c == null) {
			return null;
		}
