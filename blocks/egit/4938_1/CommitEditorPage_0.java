		List<Ref> tags = new ArrayList<Ref>(repository.getTags().values());
		Collections.sort(tags, new Comparator<Ref>() {

			public int compare(Ref r1, Ref r2) {
				return Repository.shortenRefName(r1.getName())
						.compareToIgnoreCase(
								Repository.shortenRefName(r2.getName()));
			}
		});
