
		this.processorContributions.stream().filter(sr -> {
			Dictionary<String, Object> dict = sr.getProperties();

			Object before = dict.get(IModelProcessorContribution.BEFORE_FRAGMENT_PROPERTY_KEY);
			boolean beforeFragments = true;
			if (before instanceof Boolean) {
				beforeFragments = (Boolean) before;
			} else if (before instanceof String) {
				beforeFragments = Boolean.parseBoolean((String) before);
			}

			Object applyObject = dict.get(IModelProcessorContribution.APPLY_PROPERTY_KEY);
			String apply = applyObject instanceof String ? (String) applyObject
					: IModelProcessorContribution.APPLY_ALWAYS;

			if (!ALWAYS.equals(apply) && !INITIAL.equals(apply)) {
				log(LogLevel.WARN,
						"IModelProcessorContribution apply property value \"{0}\" is invalid, falling back to always", //$NON-NLS-1$
						apply);
				apply = IModelProcessorContribution.APPLY_ALWAYS;
			}

			return ((afterFragments != beforeFragments)
					&& (initial || IModelProcessorContribution.APPLY_ALWAYS.equals(apply)));
		}).map(sr -> bundleContext.getService(sr)).forEach(ModelAssembler.this::runProcessor);
