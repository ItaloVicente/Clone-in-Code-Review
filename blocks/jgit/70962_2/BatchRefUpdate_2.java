
		if (atomic && !refdb.performsAtomicTransactions()) {
			for (ReceiveCommand c : commands) {
				if (c.getResult() == NOT_ATTEMPTED) {
					c.setResult(REJECTED_OTHER_REASON
							JGitText.get().atomicRefUpdatesNotSupported);
				}
			}
			return;
		}

