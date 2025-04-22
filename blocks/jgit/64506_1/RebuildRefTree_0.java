		Ref head = refdb.exactRef(HEAD);
		if (head != null) {
			cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(
					null
					head));
		}

		for (Ref r : refdb.getRefs(RefDatabase.ALL).values()) {
