
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append(getClass().getSimpleName());
		r.append('[');
		for (int i = 0; i < commands.size(); i++) {
			ReceiveCommand cmd = commands.get(i);
			if (i != 0) {
				r.append("
			}
			r.append(cmd);
			r.append(cmd.getResult());
			r.append(')');
		}
		r.append(']');
		return r.toString();
	}
