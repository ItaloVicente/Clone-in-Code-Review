					result.add(1, oursBeginB + commonPrefix, oursEndB
							- commonSuffix,
							ConflictState.FIRST_CONFLICTING_RANGE);
					result.add(2, theirsBeginB + commonPrefix, theirsEndB
							- commonSuffix,
							ConflictState.NEXT_CONFLICTING_RANGE);
