		if (options.contains(OPTION_MULTI_ACK_DETAILED)) {
			multiAck = MultiAck.DETAILED;
			noDone = options.contains(OPTION_NO_DONE);
		} else if (options.contains(OPTION_MULTI_ACK))
			multiAck = MultiAck.CONTINUE;
		else
			multiAck = MultiAck.OFF;
