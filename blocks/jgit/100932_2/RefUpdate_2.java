			try {
				newObj = safeParseNew(walk
			} catch (MissingObjectException e) {
				return Result.REJECTED;
			}

			if (oldValue == null) {
