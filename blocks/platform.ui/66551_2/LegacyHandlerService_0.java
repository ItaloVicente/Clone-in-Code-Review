		Expression e;
		if (expression != null) {
			AndExpression andExpr = new AndExpression();
			andExpr.add(expression);
			andExpr.add(defaultExpression);
			e = andExpr;
		} else {
			e = defaultExpression;
		}
		return registerLegacyHandler(eclipseContext, commandId, commandId, handler, e);
