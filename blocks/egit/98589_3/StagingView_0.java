	static class ControlExpression extends Expression {
		private Control control;

		public ControlExpression(Control c) {
			control = c;
		}

		@Override
		public void collectExpressionInfo(ExpressionInfo info) {
			info.addVariableNameAccess(ISources.ACTIVE_FOCUS_CONTROL_NAME);
		}

		@Override
		public EvaluationResult evaluate(IEvaluationContext context)
				throws CoreException {
			return EvaluationResult.valueOf(context.getVariable(
					ISources.ACTIVE_FOCUS_CONTROL_NAME) == control);
		}
	}

