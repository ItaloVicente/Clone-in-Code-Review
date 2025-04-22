	protected GitModelObject[] getChildrenImpl() {
		List<GitModelObject> result = new ArrayList<GitModelObject>();

		try {
			TreeWalk tw = createAndConfigureTreeWalk();

			while (tw.next()) {
				GitModelObject entry = extractFromCache(tw);
				if (entry == null)
					continue;
