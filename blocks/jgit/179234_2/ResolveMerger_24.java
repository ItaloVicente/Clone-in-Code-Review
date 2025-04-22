			MergeResult<RawText> result = null;
			try {
				result = contentMerge(base
						getContentMergeStrategy());
			} catch (BinaryBlobException e) {
				switch (getContentMergeStrategy()) {
				case OURS:
					keep(ourDce);
					return true;
				case THEIRS:
					DirCacheEntry theirEntry = add(tw.getRawPath()
							DirCacheEntry.STAGE_0
					addToCheckout(tw.getPathString()
					return true;
				default:
					result = new MergeResult<>(Collections.emptyList());
					result.setContainsConflicts(true);
					break;
				}
			}
