				Collections.sort(proposals, new Comparator<Ref>() {
					@Override
					public int compare(Ref ref1, Ref ref2) {
						return CommonUtils.REF_ASCENDING_COMPARATOR
								.compare(ref1, ref2);
					}
				});
