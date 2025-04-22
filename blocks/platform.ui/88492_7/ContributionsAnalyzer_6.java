	public static boolean isVisible(MExpression exp, final ExpressionContext eContext) {
		if (exp instanceof MCoreExpression) {
			MCoreExpression coreExpression = (MCoreExpression) exp;
			return isCoreExpressionVisible(coreExpression, eContext);
		} else if (exp instanceof MImperativeExpression) {
			return isImperativeExpressionVisible((MImperativeExpression) exp, eContext);
		}

		return true;
	}

	private static boolean isCoreExpressionVisible(MCoreExpression coreExpression, final ExpressionContext eContext) {
