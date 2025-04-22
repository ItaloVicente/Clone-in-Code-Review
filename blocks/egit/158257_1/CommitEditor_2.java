		pageListener = event -> {
			IEvaluationService service = PlatformUI.getWorkbench()
					.getService(IEvaluationService.class);
			if (service != null) {
				service.requestEvaluation(ISources.ACTIVE_PART_NAME);
			}
		};
		addPageChangedListener(pageListener);
