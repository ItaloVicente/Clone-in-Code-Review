		initialBranch.removeAll();
		final Ref actHead = head;
		int newix = 0;
		for (final Ref r : branches) {
			String name = r.getName();
			if (name.startsWith(Constants.R_HEADS))
				name = name.substring((Constants.R_HEADS).length());
			if (actHead != null && actHead.getName().equals(r.getName()))
				newix = initialBranch.getItemCount();
			initialBranch.add(name);
		}
		initialBranch.select(newix);
