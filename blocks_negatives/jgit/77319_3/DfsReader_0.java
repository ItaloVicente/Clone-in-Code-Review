		ArrayList<FoundObject<T>> r = new ArrayList<FoundObject<T>>();
		DfsPackFile[] packList = db.getPacks();
		if (packList.length == 0) {
			for (T t : objectIds)
				r.add(new FoundObject<T>(t));
			return r;
