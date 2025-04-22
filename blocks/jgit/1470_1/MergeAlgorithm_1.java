				if (conflictLen > 0) {
					result.add(1
							- commonSuffix
							ConflictState.FIRST_CONFLICTING_RANGE);
					result.add(2
							- commonSuffix
							ConflictState.NEXT_CONFLICTING_RANGE);
				}

				if (commonSuffix > 0)
					result.add(1
							ConflictState.NO_CONFLICT);
