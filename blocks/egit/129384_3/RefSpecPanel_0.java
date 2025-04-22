		if (s == null) {
			return false;
		}
		String spec = s;
		int i = spec.indexOf('*');
		if (i >= 0) {
			if (spec.indexOf('*', i + 1) >= 0) {
				return false; // Only one '*' allowed
			}
			spec = spec.replace('*', 'X');
		}
		return Repository.isValidRefName(Constants.R_HEADS + spec);
