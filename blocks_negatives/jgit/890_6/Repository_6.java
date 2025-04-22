		HashSet<ObjectId> r = new HashSet<ObjectId>();
		for (ObjectDatabase d : objectDatabase.getAlternates()) {
			if (d instanceof AlternateRepositoryDatabase) {
				Repository repo;

				repo = ((AlternateRepositoryDatabase) d).getRepository();
				for (Ref ref : repo.getAllRefs().values())
					r.add(ref.getObjectId());
				r.addAll(repo.getAdditionalHaves());
			}
		}
		return r;
