	private List<Ref> getBranches()  {
		List<Ref> ref = new ArrayList<Ref>();
		try {
			ref.addAll(db.getRefDatabase().getRefs(Constants.R_HEADS).values());
			ref.addAll(db.getRefDatabase().getRefs(Constants.R_REMOTES).values());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ref;
	}

