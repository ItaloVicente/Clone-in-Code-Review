
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("ObjectToPack[");
		buf.append(Constants.typeString(getType()));
		buf.append(" ");
		buf.append(name());
		if (wantWrite())
			buf.append(" wantWrite");
		if (isReuseAsIs())
			buf.append(" reuseAsIs");
		if (isDoNotDelta())
			buf.append(" doNotDelta");
		if (getDeltaDepth() > 0)
			buf.append(" depth=" + getDeltaDepth());
		if (isDeltaRepresentation()) {
			if (getDeltaBase() != null)
				buf.append(" base=inpack:" + getDeltaBase().name());
			else
				buf.append(" base=edge:" + getDeltaBaseId().name());
		}
		if (isWritten())
			buf.append(" offset=" + getOffset());
		buf.append("]");
		return buf.toString();
	}
