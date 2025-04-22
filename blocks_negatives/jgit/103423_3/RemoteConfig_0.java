		vlst = rc.getStringList(SECTION, name, KEY_FETCH);
		fetch = new ArrayList<>(vlst.length);
		for (final String s : vlst)
			fetch.add(new RefSpec(s));

		vlst = rc.getStringList(SECTION, name, KEY_PUSH);
		push = new ArrayList<>(vlst.length);
		for (final String s : vlst)
			push.add(new RefSpec(s));

