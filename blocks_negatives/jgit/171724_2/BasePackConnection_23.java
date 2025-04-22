	private Collection<String> getRefPrefixes(Collection<RefSpec> refSpecs,
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
				patterns.add(src.substring(0, src.indexOf('*')));
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
		String parts[] = capability.split("=", 2); //$NON-NLS-1$
		if (parts.length == 2) {
			remoteCapabilities.put(parts[0], parts[1]);
		}
		remoteCapabilities.put(capability, null);
	}

	private ObjectId toId(String line, String value)
			throws PackProtocolException {
		try {
			return ObjectId.fromString(value);
		} catch (InvalidObjectIdException e) {
			PackProtocolException ppe = invalidRefAdvertisementLine(line);
			ppe.initCause(e);
			throw ppe;
		}
	}

	private void processLineV1(String name, ObjectId id, Map<String, Ref> avail)
			throws IOException {
			name = name.substring(0, name.length() - 3);
			final Ref prior = avail.get(name);
			if (prior == null) {
				throw new PackProtocolException(uri, MessageFormat.format(
						JGitText.get().advertisementCameBefore, name, name));
			}
			if (prior.getPeeledObjectId() != null) {
			}
			avail.put(name, new ObjectIdRef.PeeledTag(Ref.Storage.NETWORK, name,
					prior.getObjectId(), id));
		} else {
			final Ref prior = avail.put(name, new ObjectIdRef.PeeledNonTag(
					Ref.Storage.NETWORK, name, id));
			if (prior != null) {
				throw duplicateAdvertisement(name);
			}
		}
	}

	private void processLineV2(String line, ObjectId id, String rest,
			Map<String, Ref> avail) throws IOException {
		String name = parts[0];
		String symRefTarget = null;
		String peeled = null;
		for (int i = 1; i < parts.length; i++) {
			if (parts[i].startsWith(REF_ATTR_SYMREF_TARGET)) {
				if (symRefTarget != null) {
					throw new PackProtocolException(uri, MessageFormat.format(
							JGitText.get().duplicateRefAttribute, line));
				}
				symRefTarget = parts[i]
						.substring(REF_ATTR_SYMREF_TARGET.length());
			} else if (parts[i].startsWith(REF_ATTR_PEELED)) {
				if (peeled != null) {
					throw new PackProtocolException(uri, MessageFormat.format(
							JGitText.get().duplicateRefAttribute, line));
				}
				peeled = parts[i].substring(REF_ATTR_PEELED.length());
			}
			if (peeled != null && symRefTarget != null) {
				break;
			}
		}
		Ref idRef;
		if (peeled != null) {
			idRef = new ObjectIdRef.PeeledTag(Ref.Storage.NETWORK, name, id,
					toId(line, peeled));
		} else {
			idRef = new ObjectIdRef.PeeledNonTag(Ref.Storage.NETWORK, name, id);
		}
		Ref prior = avail.put(name, idRef);
		if (prior != null) {
			throw duplicateAdvertisement(name);
		}
		if (symRefTarget != null) {
		}
	}

