	private CompletableFuture<NestedProjectsProblemsModel> refreshSeverities() {
		return CompletableFuture.supplyAsync(() -> {
			model.refreshModel();
			return model;
		});
	}
