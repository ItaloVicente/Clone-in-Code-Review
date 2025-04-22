					MergeResult<RawText> result;
					try {
						result = contentMerge(base
								ContentMergeStrategy.CONFLICT);
					} catch (BinaryBlobException e) {
						result = new MergeResult<>(Collections.emptyList());
						result.setContainsConflicts(true);
					}
