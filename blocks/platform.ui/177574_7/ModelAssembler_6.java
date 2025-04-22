
		if (this.processorContributions != null) {
			this.processorContributions.stream().filter(p -> afterFragments != p.isBeforeFragment())
					.filter(p -> initial || !p.test(ApplyAttribute.INITIAL))
					.forEach(ModelAssembler.this::runProcessor);
		}
