	private boolean matchNegativeRefSpec(RefSpec spec) {
		for (RefSpec negativeRefSpec : negativeRefSpecs) {
			if (negativeRefSpec.getSource() != null
					&& negativeRefSpec.matchSource(spec.getSource())) {
				return true;
			}

			if (negativeRefSpec.getDestination() != null && negativeRefSpec
					.matchDestination(spec.getDestination())) {
				return true;
			}
		}
		return false;
	}

