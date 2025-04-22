		Ref master = db.exactRef("refs/heads/master");
		String message = msgFormatter.format(Arrays.asList(revCommit), master);
		assertEquals(
				"Squashed commit of the following:\n\ncommit "
						+ revCommit.getName() + "\nAuthor: "
						+ revCommit.getAuthorIdent().getName() + " <"
						+ revCommit.getAuthorIdent().getEmailAddress()
						+ ">\nDate:   " + dateFormatter.formatDate(author)
						+ "\n\n\tsquash_me\n", message);
