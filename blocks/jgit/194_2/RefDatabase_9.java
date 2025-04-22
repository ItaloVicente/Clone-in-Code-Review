		final String rName = name + '/';
		for (String other : all.keySet()) {
			if (other.startsWith(rName))
				return true;
		}

		return false;
