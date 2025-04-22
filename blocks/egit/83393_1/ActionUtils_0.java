	private static class FocusExpression extends Expression {

		private final ActiveShellExpression shellExpression;

		private final Control control;

		private final int controlHash;

		public FocusExpression(Control control) {
			this.shellExpression = new ActiveShellExpression(
					control.getShell());
			this.control = control;
			this.controlHash = control.hashCode();
		}

		@Override
		public void collectExpressionInfo(final ExpressionInfo info) {
			this.shellExpression.collectExpressionInfo(info);
		}

		@Override
		public int hashCode() {
			return this.shellExpression.hashCode() ^ controlHash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj instanceof FocusExpression) {
				FocusExpression other = (FocusExpression) obj;
				return this.shellExpression.equals(other.shellExpression)
						&& this.control == other.control;
			}
			return false;
		}

		@Override
		public final EvaluationResult evaluate(
				final IEvaluationContext context) {
			EvaluationResult result = shellExpression.evaluate(context);
			if (EvaluationResult.TRUE.equals(result)
					&& !control.isDisposed() && control.isFocusControl()) {
				return result;
			}
			return EvaluationResult.FALSE;
		}
	}

