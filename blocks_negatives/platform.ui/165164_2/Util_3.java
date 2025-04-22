	public static boolean startsWith(Object[] left, Object[] right, boolean equals) {
		if (left == null || right == null) {
			return false;
		} else {
			int l = left.length;
			int r = right.length;

			if (r > l || !equals && r == l) {
				return false;
			}

			for (int i = 0; i < r; i++) {
				if (!Objects.equals(left[i], right[i])) {
					return false;
				}
			}

			return true;
		}
	}

