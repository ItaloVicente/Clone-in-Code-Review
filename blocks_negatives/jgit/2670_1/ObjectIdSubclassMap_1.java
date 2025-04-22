		int i = index(newValue);
		V obj;

		while ((obj = obj_hash[i]) != null) {
			if (AnyObjectId.equals(obj, newValue))
				return obj;
			if (++i == obj_hash.length)
				i = 0;
		}
