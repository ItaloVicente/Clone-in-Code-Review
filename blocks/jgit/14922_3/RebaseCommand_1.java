			if (steps.isEmpty() && !rebaseState.getFile(DONE).exists())
				return abort(RebaseResult.NOTHING_TO_DO_RESULT);
			StringBuilder sb = new StringBuilder();
			for (; !steps.isEmpty(); steps = loadSteps()) {
				Step step = steps.poll();
				if (step == null) {
					continue;
				}
