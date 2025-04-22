	private class BindStepImpl implements BindStep {
		@Override
		public Binding bind(DataBindingContext context) {
			return bindValue(context);
		}
	}

	private final class TargetSetStepImpl<T> extends BindStepImpl implements TargetSetStep<T> {
		@Override
		public BindStep validateTarget(IValidator<? super T> validator) {
			toTarget.setBeforeSetValidator(validator);
			return new BindStepImpl();
		}

	}

