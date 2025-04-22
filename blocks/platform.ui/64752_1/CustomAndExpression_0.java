	@Override
	public void collectExpressionInfo(ExpressionInfo info) {
		if (fExpressions == null)
			return;
		for (Iterator<Expression> iter = fExpressions.iterator(); iter.hasNext();) {
			Expression expression = iter.next();
			expression.collectExpressionInfo(info);
		}
	}

