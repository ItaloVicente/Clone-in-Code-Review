
		if (atomic && !refdb.performsAtomicTransactions()) {
			ReceiveCommand.abort(commands);
			return;
		}

