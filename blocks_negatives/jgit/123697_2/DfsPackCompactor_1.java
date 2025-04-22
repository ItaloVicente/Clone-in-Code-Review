		Collections.sort(srcPacks, new Comparator<DfsPackFile>() {
			@Override
			public int compare(DfsPackFile a, DfsPackFile b) {
				return a.getPackDescription().compareTo(b.getPackDescription());
			}
		});
