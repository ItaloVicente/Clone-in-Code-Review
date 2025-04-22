		if (notAdvertisedWants != null && !notAdvertisedWants.isEmpty()) {
			switch (requestPolicy) {
			case ADVERTISED:
			default:
				throw new PackProtocolException(MessageFormat.format(
						JGitText.get().wantNotValid,
						notAdvertisedWants.iterator().next().name()));

			case REACHABLE_COMMIT:
				checkNotAdvertisedWants(notAdvertisedWants);
				break;

			case ANY:
				break;
			}
		}

