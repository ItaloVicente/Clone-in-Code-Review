		int direction;
		Direction gitDirection = obj.getDiffEntry().getDirection();
		if (gitDirection == Direction.INCOMING)
			direction = INCOMING;
		else if (gitDirection == Direction.OUTGOING)
			direction = OUTGOING;
		else
			direction = CONFLICTING;
