		Calendar cal = Calendar.getInstance();
		PersonIdent sideCommitter = new PersonIdent("Side Committer",
				"side@example.org", cal.getTime().getTime(), 0);
		cal.roll(Calendar.SECOND, 2);
		PersonIdent masterCommitter = new PersonIdent("Master Committer",
				"master@example.org", cal.getTime().getTime(), 0);

