			Candidate best = Collections.min(candidates, new Comparator<Candidate>() {
				@Override
				public int compare(Candidate o1, Candidate o2) {
					return o1.depth - o2.depth;
				}
			});
