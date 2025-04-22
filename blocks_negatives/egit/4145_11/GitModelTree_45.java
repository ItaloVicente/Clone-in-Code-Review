	protected GitModelObject[] getChildrenImpl() {
		TreeWalk tw = createTreeWalk();
		List<GitModelObject> result = new ArrayList<GitModelObject>();

		try {
			int remoteNth = -1;
			if (!remoteId.equals(zeroId()))
				remoteNth = tw.addTree(remoteId);

			int baseNth = -1;
			if (!baseId.equals(zeroId()))
				baseNth = tw.addTree(baseId);

			int ancestorNth = -1;
			if (!ancestorId.equals(zeroId()))
				ancestorNth = tw.addTree(ancestorId);

			while (tw.next()) {
				GitModelObject obj = getModelObject(tw, ancestorCommit, ancestorNth,
						remoteNth, baseNth);
				if (obj != null)
					result.add(obj);
			}
		} catch (IOException e) {
			Activator.logError(e.getMessage(), e);
		}

		return result.toArray(new GitModelObject[result.size()]);
