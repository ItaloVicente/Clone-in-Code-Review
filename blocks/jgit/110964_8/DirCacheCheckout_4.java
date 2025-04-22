
	private boolean skipSparse(DirCacheEntry entry) {
		if (sparseRules.isEmpty()) {
			return false;
		}

		boolean skip = sparseRules.isEmpty() ? false : true;
		final boolean isDirectory = FileMode.TREE.equals(entry.getFileMode());

		for (int i = sparseRules.size() - 1; i >= 0; i--) {
			final FastIgnoreRule sparseRule = sparseRules.get(i);

			if (sparseRule.isMatch(entry.getPathString()
				skip = !sparseRule.getResult();
				break;
			}
		}

		return skip;
	}

	private void loadSparseCheckoutRules()
			throws FileNotFoundException
		sparseRules.clear();

		final boolean isSparseCheckout = repo.getConfig().getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
		final File sparseCheckoutFile = repo.getSparseCheckoutFile();

		if (!isSparseCheckout || !FS.DETECTED.exists(sparseCheckoutFile)) {
			return;
		}

		if (isSparseCheckout && FS.DETECTED.exists(sparseCheckoutFile)) {
			try (FileInputStream in = new FileInputStream(sparseCheckoutFile);
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

