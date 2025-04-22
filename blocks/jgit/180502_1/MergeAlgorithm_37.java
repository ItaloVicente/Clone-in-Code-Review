				switch (strategy) {
				case OURS:
					result.add(1
					break;
				case THEIRS:
					result.add(2
					break;
				default:
					result.add(1
							ConflictState.FIRST_CONFLICTING_RANGE);
					result.add(2
					break;
				}
			} else {
