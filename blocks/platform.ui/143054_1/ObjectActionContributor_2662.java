			}
			if (enablement != null) {
				try {
					IEvaluationContext context = new EvaluationContext(null, object);
					context.setAllowPluginActivation(true);
					context.addVariable("selection", object); //$NON-NLS-1$
					context.addVariable("org.eclipse.core.runtime.Platform", Platform.class); //$NON-NLS-1$
					EvaluationResult evalResult = enablement.evaluate(context);
					if (evalResult == EvaluationResult.FALSE) {
