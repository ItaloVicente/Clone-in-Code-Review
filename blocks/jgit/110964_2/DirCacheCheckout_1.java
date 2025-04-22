
	private boolean skipSparse(DirCacheEntry entry) {
		if (sparseRules.isEmpty()) {
			return true;
		}

		boolean skip = sparseRules.isEmpty() ? false : true;
		boolean isDirectory = FileMode.TREE.equals(entry.getFileMode());

		for (FastIgnoreRule sparseRule : sparseRules) {
			if (sparseRule.isMatch(entry.getPathString()
					isDirectory)) {

				if (skip && !sparseRule.getNegation()) {
					final boolean checkout = sparseRule.getResult();
					if (checkout) {
						skip = false;
					}
				}

				if (!skip && sparseRule.getNegation()) {
					final boolean checkout = sparseRule.getResult();
					if (!checkout) {
						skip = true;
						break;
					}
				}
			}
		}

		return skip;
	}

	private void loadSparseCheckoutRules()
			throws FileNotFoundException
		StoredConfig rc = repo.getConfig();
		final boolean isSparseCheckout = rc.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SPARSECHECKOUT

		final File file = repo.getSparseCheckoutFile();

		if (!isSparseCheckout || !FS.DETECTED.exists(file)) {
			return;
		}

		if (isSparseCheckout && FS.DETECTED.exists(file)) {
			try (FileInputStream in = new FileInputStream(file);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(in
				String line;

				while ((line = br.readLine()) != null) {
						FastIgnoreRule rule = new FastIgnoreRule(line.trim());

						if (!rule.isEmpty()) {
							sparseRules.add(rule);
						}
					}
				}
			}
		}
	}

