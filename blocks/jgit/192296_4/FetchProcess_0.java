		initializeNegativeRefSpecs(n);
	}

	private void initializeNegativeRefSpecs(Collection<RefSpec> n) {
		if(n == null){
			return;
		}

		for (RefSpec spec : n) {
			if (spec.getSource() != null && spec.getDestination() != null) {
				throw new IllegalArgumentException(MessageFormat
						.format(JGitText.get().invalidNegativeRefSpec
			}

			if (spec.getSource() == null && spec.getDestination() != null) {
				negativeDestRefSpecs.add(spec);
			}

			if (spec.getSource() != null && spec.getDestination() == null) {
				negativeSrcRefSpecs.add(spec);
			}
		}
