		for (;;) {
			String line = readLine();
			if (line == null) {
				break;
			}
			if (line.length() < 41 || line.charAt(40) != ' ') {
				throw invalidRefAdvertisementLine(line);
			}
			String name = line.substring(41
			final ObjectId id = toId(line
				additionalHaves.add(id);
			} else {
				processLineV2(line
			}
		}
		updateWithSymRefs(avail
		available(avail);
	}

	private Collection<String> getRefPrefixes(Collection<RefSpec> refSpecs
			String... additionalPatterns) {
		if (refSpecs.isEmpty() && (additionalPatterns == null
				|| additionalPatterns.length == 0)) {
			return Collections.emptyList();
		}
		Set<String> patterns = new HashSet<>();
		if (additionalPatterns != null) {
			Arrays.stream(additionalPatterns).filter(Objects::nonNull)
					.forEach(patterns::add);
		}
		for (RefSpec spec : refSpecs) {
			String src = spec.getSource();
			if (ObjectId.isId(src)) {
				continue;
			}
			if (spec.isWildcard()) {
				patterns.add(src.substring(0
			} else {
				patterns.add(src);
				patterns.add(Constants.R_REFS + src);
				patterns.add(Constants.R_HEADS + src);
				patterns.add(Constants.R_TAGS + src);
			}
		}
		return patterns;
	}

	private void readCapabilitiesV2() throws IOException {
		for (;;) {
			String line = readLine();
			if (line == null) {
				break;
			}
			addCapability(line);
		}
	}

	private void addCapability(String capability) {
		String parts[] = capability.split("="
		if (parts.length == 2) {
			remoteCapabilities.put(parts[0]
		}
		remoteCapabilities.put(capability
	}

	private ObjectId toId(String line
			throws PackProtocolException {
		try {
			return ObjectId.fromString(value);
		} catch (InvalidObjectIdException e) {
			PackProtocolException ppe = invalidRefAdvertisementLine(line);
			ppe.initCause(e);
			throw ppe;
		}
	}

	private void processLineV1(String name
			throws IOException {
			name = name.substring(0
			final Ref prior = avail.get(name);
			if (prior == null) {
				throw new PackProtocolException(uri
						JGitText.get().advertisementCameBefore
			}
			if (prior.getPeeledObjectId() != null) {
			}
			avail.put(name
					prior.getObjectId()
		} else {
			final Ref prior = avail.put(name
					Ref.Storage.NETWORK
			if (prior != null) {
				throw duplicateAdvertisement(name);
			}
		}
	}

	private void processLineV2(String line
			Map<String
			throws IOException {
		String name = parts[0];
		String symRefTarget = null;
		String peeled = null;
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].startsWith(REF_ATTR_SYMREF_TARGET)) {
				if (symRefTarget != null) {
					throw new PackProtocolException(uri
							JGitText.get().duplicateRefAttribute
				}
				symRefTarget = parts[i]
						.substring(REF_ATTR_SYMREF_TARGET.length());
			} else if (parts[i].startsWith(REF_ATTR_PEELED)) {
				if (peeled != null) {
					throw new PackProtocolException(uri
							JGitText.get().duplicateRefAttribute
