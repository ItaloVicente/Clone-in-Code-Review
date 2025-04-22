			if (expectedValue instanceof Integer) {
				return commit.getParentCount() <= ((Integer) expectedValue)
						.intValue();
			} else {
				return computeResult(expectedValue,
						commit.getParentCount() > 0);
