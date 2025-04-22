		Binding bind(DataBindingContext context);

		ConfigStep<T, M> validateModelAfterGet(IValidator<? super T> validator);

		ConfigStep<T, M> validateModelAfterConvert(IValidator<? super M> validator);

		ConfigStep<T, M> validateModelBeforeSet(IValidator<? super M> validator);

		ConfigStep<T, M> validateTargetAfterGet(IValidator<? super M> validator);

		ConfigStep<T, M> validateTargetAfterConvert(IValidator<? super T> validator);

		ConfigStep<T, M> validateTargetBeforeSet(IValidator<? super T> validator);

