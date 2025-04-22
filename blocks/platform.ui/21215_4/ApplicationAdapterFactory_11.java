			public Adapter caseLifecycleAware(MLifecycleAware object) {
				return createLifecycleAwareAdapter();
			}
			@Override
			public Adapter caseLifecycleContribution(MLifecycleContribution object) {
				return createLifecycleContributionAdapter();
			}
			@Override
