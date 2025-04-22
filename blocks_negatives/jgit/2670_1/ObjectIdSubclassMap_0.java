		int i = index(toFind);
		V obj;

		while ((obj = obj_hash[i]) != null) {
			if (AnyObjectId.equals(obj, toFind))
				return obj;
			if (++i == obj_hash.length)
				i = 0;
		}
		return null;
