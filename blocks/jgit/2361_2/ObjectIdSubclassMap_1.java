	public <Q extends V> V addIfAbsent(final Q newValue) {
		int i = index(newValue);
		V obj;

		while ((obj = obj_hash[i]) != null) {
			if (AnyObjectId.equals(obj
				return obj;
			if (++i == obj_hash.length)
				i = 0;
		}

		if (obj_hash.length - 1 <= size * 2) {
			grow();
			insert(newValue);
		} else {
			obj_hash[i] = newValue;
		}
		size++;
		return newValue;
	}

