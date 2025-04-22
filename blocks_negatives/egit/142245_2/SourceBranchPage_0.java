		Collections.sort(availableRefs, new Comparator<Ref>() {
			@Override
			public int compare(final Ref ref1, final Ref ref2) {
				return CommonUtils.REF_ASCENDING_COMPARATOR.compare(ref1, ref2);
			}
		});
