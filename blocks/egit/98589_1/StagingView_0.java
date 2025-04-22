	static class CopyHandler extends AbstractHandler {
		private StructuredViewer viewer;

		public CopyHandler(StructuredViewer viewer) {
			this.viewer = viewer;
		}

		@Override
		public Object execute(ExecutionEvent event) throws ExecutionException {
			Clipboard cb = new Clipboard(viewer.getControl().getDisplay());
			TextTransfer t = TextTransfer.getInstance();
			Optional<String> text = getText();
			if (!text.isPresent()) {
				return null;
			}
			cb.setContents(new Object[] { text.get() }, new Transfer[] { t });
			cb.dispose();
			return null;
		}

		private Optional<String> getText() {
			IStructuredSelection ssel = viewer.getStructuredSelection();
			Object obj = ssel.getFirstElement();
			if (obj instanceof StagingEntry) {
				return Optional.of(((StagingEntry) obj).getFile().toString());
			}
			return Optional.empty();
		}
	}

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

