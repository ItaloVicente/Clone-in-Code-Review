	@Override
	public int hashCode() {
		if (elements == null) {
			return Objects.hash(comparer);
		}

		int r;
		if (comparer != null) {
			r = 31 + comparer.hashCode();
			for (Object e : elements) {
				r = 31 * r + (e == null ? 0 : comparer.hashCode(e));
			}
		} else {
			r = Arrays.hashCode(elements);
		}

		return r;
	}

