
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
				&& pushee.equals(p.pushee)
				&& nonceStatus == p.nonceStatus
				&& rawCommands.equals(p.rawCommands)
				&& signature.equals(p.signature);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '['
				 + toText() + signature + ']';
	}
