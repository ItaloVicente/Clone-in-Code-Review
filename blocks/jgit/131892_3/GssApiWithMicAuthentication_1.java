		while (GssApiMechanisms.SPNEGO.equals(currentMechanism)) {
			if (!nextMechanism.hasNext()) {
				return false;
			}
			currentMechanism = nextMechanism.next();
		}
