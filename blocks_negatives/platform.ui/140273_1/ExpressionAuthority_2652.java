				contextWithDefaultVariable = new EvaluationContext(context,
						selection.toList());
			} else if ((defaultVariable instanceof ISelection)
					&& (!((ISelection) defaultVariable).isEmpty())) {
				contextWithDefaultVariable = new EvaluationContext(context,
						Collections.singleton(defaultVariable));
