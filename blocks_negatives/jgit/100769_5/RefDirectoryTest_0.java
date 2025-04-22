	private static ReceiveCommand newCommand(RevCommit a, RevCommit b,
			String string, Type update) {
		return new ReceiveCommand(a != null ? a.getId() : null,
				b != null ? b.getId() : null, string, update);
	}

