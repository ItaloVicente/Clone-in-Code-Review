		Object context = event.getApplicationContext();
		if (!(context instanceof IEvaluationContext))
			return;

		final Repository repository = SelectionUtils
				.getRepository((IEvaluationContext) context);
