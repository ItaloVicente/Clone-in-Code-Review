			if (rf instanceof MaxCountRevFilter) {
				rf = MaxCountRevFilter.and(
						new TreeRevFilter(w
						(MaxCountRevFilter) rf);
			} else {
				rf = AndRevFilter.create(new TreeRevFilter(w
						rf);
			}
