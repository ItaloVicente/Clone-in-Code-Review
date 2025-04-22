		return isVisible(contribution.getVisibleWhen(), eContext);
	}

	public static boolean isVisible(MExpression exp, final ExpressionContext eContext) {
		if (exp instanceof MCoreExpression) {
			MCoreExpression coreExpression = (MCoreExpression) exp;
			return isCoreExpressionVisible(coreExpression, eContext);
		} else if (exp instanceof MImperativeExpression) {
			return isImperativeExpressionVisible((MImperativeExpression) exp, eContext);
		}

		return true;
