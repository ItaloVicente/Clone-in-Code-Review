			this(advertisement, Collections.emptyList());
		}

		SmartHttpFetchConnection(InputStream advertisement,
				Collection<RefSpec> refSpecs, String... additionalPatterns)
				throws TransportException {
