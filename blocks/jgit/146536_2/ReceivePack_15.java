	}

	protected void sendStatusReport(final boolean forClient
			final Throwable unpackError
			throws IOException {
		if (unpackError != null) {
			if (forClient) {
				for (ReceiveCommand cmd : commands) {
				}
			}
			return;
		}

		if (forClient)
		for (ReceiveCommand cmd : commands) {
			if (cmd.getResult() == Result.OK) {
				if (forClient)
				continue;
			}

			final StringBuilder r = new StringBuilder();
			if (forClient)
			else

			switch (cmd.getResult()) {
			case NOT_ATTEMPTED:
				break;

			case REJECTED_NOCREATE:
				break;

			case REJECTED_NODELETE:
				break;

			case REJECTED_NONFASTFORWARD:
				break;

			case REJECTED_CURRENT_BRANCH:
				break;

			case REJECTED_MISSING_OBJECT:
				if (cmd.getMessage() == null)
				else if (cmd.getMessage()
						.length() == Constants.OBJECT_ID_STRING_LENGTH) {
					r.append(cmd.getMessage());
				} else
					r.append(cmd.getMessage());
				break;

			case REJECTED_OTHER_REASON:
				if (cmd.getMessage() == null)
				else
					r.append(cmd.getMessage());
				break;

			case LOCK_FAILURE:
				break;

			case OK:
				continue;
			}
			if (!forClient)
			out.sendString(r.toString());
		}
	}

	protected void close() throws IOException {
		if (sideBand) {
			((SideBandOutputStream) msgOut).flushBuffer();
			((SideBandOutputStream) rawOut).flushBuffer();

			PacketLineOut plo = new PacketLineOut(origOut);
			plo.setFlushOnEnd(false);
			plo.end();
		}

		if (biDirectionalPipe) {
			if (!sideBand && msgOut != null)
				msgOut.flush();
			rawOut.flush();
		}
	}

	protected void release() throws IOException {
		walk.close();
		unlockPack();
		timeoutIn = null;
		rawIn = null;
		rawOut = null;
		msgOut = null;
		pckIn = null;
		pckOut = null;
		refs = null;
		commands = null;
		if (timer != null) {
			try {
				timer.terminate();
			} finally {
				timer = null;
			}
