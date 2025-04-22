					switch (strategy) {
					case OURS:
						result.add(1
								oursEndB - commonSuffix
								ConflictState.NO_CONFLICT);
						break;
					case THEIRS:
						result.add(2
								theirsEndB - commonSuffix
								ConflictState.NO_CONFLICT);
						break;
					default:
						result.add(1
								oursEndB - commonSuffix
								ConflictState.FIRST_CONFLICTING_RANGE);
						result.add(2
								theirsEndB - commonSuffix
								ConflictState.NEXT_CONFLICTING_RANGE);
						break;
					}
