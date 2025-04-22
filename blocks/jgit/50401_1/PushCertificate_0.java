		text = new StringBuilder()
				.append(VERSION).append(' ').append(version).append('\n')
				.append(PUSHER).append(' ').append(getPusher())
				.append('\n')
				.append(PUSHEE).append(' ').append(pushee).append('\n')
				.append(NONCE).append(' ').append(nonce).append('\n')
				.append('\n')
				.append(rawCommands)
				.toString();

		try (ObjectInserter ins = db.newObjectInserter()) {
			byte[] data = Constants.encode(toText() + signature);
			objectId = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}
