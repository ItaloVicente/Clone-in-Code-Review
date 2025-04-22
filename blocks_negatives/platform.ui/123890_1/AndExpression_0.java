	@Override
	public final String toString() {
		final StringBuilder buffer = new StringBuilder();
		if (fExpressions != null) {
			final Iterator itr = fExpressions.iterator();
			while (itr.hasNext()) {
				final Expression expression = (Expression) itr.next();
				buffer.append(expression.toString());
				if (itr.hasNext()) {
					buffer.append(',');
				}
			}
		}
		buffer.append(')');
		return buffer.toString();
	}
