
	@Override
	public int hashCode() {
		return signature.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PushCertificate)) {
			return false;
		}
		PushCertificate p = (PushCertificate) o;
		return version.equals(p.version)
				&& pusher.equals(p.pusher)
				&& Objects.equals(pushee
				&& nonceStatus == p.nonceStatus
				&& signature.equals(p.signature)
				&& commandsEqual(this
	}

	private static boolean commandsEqual(PushCertificate c1
		if (c1.commands.size() != c2.commands.size()) {
			return false;
		}
		for (int i = 0; i < c1.commands.size(); i++) {
			ReceiveCommand cmd1 = c1.commands.get(i);
			ReceiveCommand cmd2 = c2.commands.get(i);
			if (!cmd1.getOldId().equals(cmd2.getOldId())
					|| !cmd1.getNewId().equals(cmd2.getNewId())
					|| !cmd1.getRefName().equals(cmd2.getRefName())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '['
				 + toText() + signature + ']';
	}
