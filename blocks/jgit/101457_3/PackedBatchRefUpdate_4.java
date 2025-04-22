	private void writeReflog(List<ReceiveCommand> commands) {
		if (getRefLogMessage() == null && !isRefLogIncludingResult()) {
			return;
		}

		ReflogWriter w = refdb.getLogWriter();
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() != ReceiveCommand.Result.OK) {
				continue;
			}
			String name = cmd.getRefName();

			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				try {
					RefDirectory.delete(w.logFor(name)
				} catch (IOException e) {
				}
				continue;
			}

			String msg = getRefLogMessage();
			if (isRefLogIncludingResult()) {
				String strResult = toResultString(cmd);
				if (strResult != null) {
					msg = msg.isEmpty()
				}
			}
			PersonIdent ident = getRefLogIdent();
			if (ident == null) {
				ident = new PersonIdent(refdb.getRepository());
			}
			try {
				w.log(name
			} catch (IOException e) {
			}
		}
	}

	private String toResultString(ReceiveCommand cmd) {
		switch (cmd.getType()) {
		case CREATE:
			return ReflogEntry.PREFIX_CREATED;
		case UPDATE:
			return isAllowNonFastForwards()
					? ReflogEntry.PREFIX_FORCED_UPDATE : ReflogEntry.PREFIX_FAST_FORWARD;
		case UPDATE_NONFASTFORWARD:
			return ReflogEntry.PREFIX_FORCED_UPDATE;
		default:
			return null;
		}
	}

