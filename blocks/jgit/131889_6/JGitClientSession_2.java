	@Override
	protected String resolveAvailableSignaturesProposal(
			FactoryManager manager) {
		Set<String> defaultSignatures = new LinkedHashSet<>();
		defaultSignatures.addAll(getSignatureFactoriesNames());
		HostConfigEntry config = resolveAttribute(
				JGitSshClient.HOST_CONFIG_ENTRY);
		String hostKeyAlgorithms = config
				.getProperty(SshConstants.HOST_KEY_ALGORITHMS);
		if (hostKeyAlgorithms != null && !hostKeyAlgorithms.isEmpty()) {
			char first = hostKeyAlgorithms.charAt(0);
			if (first == '+') {
				return String.join("
			if (toRemove.indexOf('*') < 0 && toRemove.indexOf('?') < 0) {
				current.remove(toRemove);
				continue;
			}
			try {
				FileNameMatcher matcher = new FileNameMatcher(toRemove
				for (Iterator<String> i = current.iterator(); i.hasNext();) {
					matcher.reset();
					matcher.append(i.next());
					if (matcher.isMatch()) {
						i.remove();
					}
				}
			} catch (InvalidPatternException e) {
				log.warn(format(SshdText.get().configInvalidPattern
						toRemove));
			}
		}
	}

	private List<String> filteredList(Set<String> known
		List<String> newNames = new ArrayList<>();
		for (String newValue : values.split("\\s*
			if (known.contains(newValue)) {
				newNames.add(newValue);
			}
		}
		return newNames;
	}

