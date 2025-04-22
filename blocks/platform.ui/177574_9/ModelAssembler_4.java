		if (this.tracker != null) {
			this.tracker.open();

			List<ModelFragmentWrapper> collect = this.tracker.getTracked().values().stream()
					.flatMap(List::stream).map(w -> w.wrapper).collect(Collectors.toList());
			wrappers.addAll(collect);
		}

