
		if (ours.size() == 0) {
			if (theirs.size() != 0) {
				EditList theirsEdits = diffAlg.diff(cmp
				if (!theirsEdits.isEmpty()) {
					result.add(1
					result.add(2
							ConflictState.NEXT_CONFLICTING_RANGE);
				} else
					result.add(1
			} else
				result.add(1
			return result;
		} else if (theirs.size() == 0) {
			EditList oursEdits = diffAlg.diff(cmp
			if (!oursEdits.isEmpty()) {
				result.add(1
						ConflictState.FIRST_CONFLICTING_RANGE);
				result.add(2
			} else
				result.add(2
			return result;
		}

