		try (TreeWalk walk = TreeWalk.forPath(db
			ObjectLoader loader = db.open(walk.getObjectId(0)
					Constants.OBJ_BLOB);
			String result = RawParseUtils.decode(loader.getCachedBytes());
			return result;
		}
