		Expression e;
		if (expression != null) {
			AndExpression andExpr = new AndExpression();
			andExpr.add(expression);
			andExpr.add(defaultExpression);
			e = andExpr;
		} else {
			e = expression;
		}
		return registerLegacyHandler(eclipseContext, commandId, commandId, handler, e);
