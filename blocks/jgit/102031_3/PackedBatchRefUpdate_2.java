		try {
			refdb.pack(
					pending.stream().map(ReceiveCommand::getRefName).collect(toList()));
		} catch (LockFailedException e) {
			lockFailure(pending.get(0)
			return;
		}
