		if (this.tracker != null) {
			this.tracker.open();

			List<ModelFragmentWrapper> collect = this.tracker.getTracked().values().stream()
					.map(mapping -> mapping.wrapper).collect(Collectors.toList());
			wrappers.addAll(collect);
		}

