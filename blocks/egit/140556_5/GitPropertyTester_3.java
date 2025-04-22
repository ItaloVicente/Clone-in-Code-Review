		if (property != null) {
			switch (property) {
			case "parentCount": //$NON-NLS-1$
			{
				RevCommit commit = AdapterUtils.adapt(receiver,
						RevCommit.class);
				if (commit == null) {
					return false;
				}
				if (expectedValue instanceof Integer) {
					return commit.getParentCount() <= ((Integer) expectedValue)
							.intValue();
				} else {
					return computeResult(expectedValue,
							commit.getParentCount() > 0);
				}
