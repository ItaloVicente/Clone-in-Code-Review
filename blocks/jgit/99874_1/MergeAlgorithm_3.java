		} else if (GitAttributes.isBinary(attributes)) {
			result.add(1
					ConflictState.FIRST_CONFLICTING_RANGE);
			result.add(2
					ConflictState.NEXT_CONFLICTING_RANGE);
			return result;
