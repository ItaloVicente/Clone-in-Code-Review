		}, new Expression() {

			@Override
			public void collectExpressionInfo(ExpressionInfo info) {
				info.addVariableNameAccess(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			}

			@Override
			public EvaluationResult evaluate(IEvaluationContext context) {
				return EvaluationResult.TRUE;
			}
