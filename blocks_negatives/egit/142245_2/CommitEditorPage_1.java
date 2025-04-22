		Collections.sort(tags, new Comparator<Ref>() {

			@Override
			public int compare(Ref r1, Ref r2) {
				return CommonUtils.STRING_ASCENDING_COMPARATOR.compare(
						Repository.shortenRefName(r1.getName()),
						Repository.shortenRefName(r2.getName()));
			}
		});
