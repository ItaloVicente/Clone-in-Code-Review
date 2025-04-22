			DfsStreamKey revKey = new DfsStreamKey.ForReverseIndex(
					desc.getStreamKey(INDEX));
			DfsBlockCache.Ref<PackReverseIndex> revref = cache
					.getOrLoadRef(revKey
						PackReverseIndex revidx = new PackReverseIndex(idx);
						int sz = (int) Math.min(idx.getObjectCount() * 8
								Integer.MAX_VALUE);
						reverseIndex = revidx;
						return new DfsBlockCache.Ref<>(revKey
					});
			PackReverseIndex revidx = revref.get();
			if (reverseIndex == null && revidx != null) {
				reverseIndex = revidx;
			}
			return reverseIndex;
