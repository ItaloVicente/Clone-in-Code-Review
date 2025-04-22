					MergeResult<SubmoduleConflict> result = new MergeResult<>(
							Arrays.asList(
									new SubmoduleConflict(base == null ? null
											: base.getEntryObjectId()),
									new SubmoduleConflict(ours == null ? null
											: ours.getEntryObjectId()),
									new SubmoduleConflict(theirs == null ? null
											: theirs.getEntryObjectId())));
