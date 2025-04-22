		if (branchNameProvider != null)
			SafeRunner.run(new SafeRunnable() {
				@Override
				public void run() throws Exception {
					ref.set(branchNameProvider.getBranchNameSuggestion());
				}
			});
