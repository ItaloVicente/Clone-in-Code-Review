
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append(getClass().getSimpleName()).append('[');
		if (commands.isEmpty())
			return r.append(']').toString();

		r.append('\n');
		for (ReceiveCommand cmd : commands) {
			r.append(cmd);
		}
		return r.append(']').toString();
	}
