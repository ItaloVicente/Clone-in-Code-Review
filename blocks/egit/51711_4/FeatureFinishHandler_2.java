			featureBranch = gfRepo.getRepository().getBranch();
		} catch (IOException e) {
			return error(e.getMessage(), e);
		}

		FinishFeatureDialog dialog = new FinishFeatureDialog(
				HandlerUtil.getActiveShell(event), featureBranch);
		if (dialog.open() != Window.OK) {
			return null;
		}
		boolean squash = dialog.isSquash();

		try {
			FeatureFinishOperation operation = new FeatureFinishOperation(
					gfRepo);
			operation.setSquash(squash);
