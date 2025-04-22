				MergeResult<RawText> result;
				try {
					result = contentMerge(base
				} catch (BinaryException e) {
					result = new MergeResult<>(Collections.<RawText> emptyList());
					result.setContainsConflicts(true);
				}
