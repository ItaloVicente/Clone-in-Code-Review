
	private boolean skipSparse(String path) {
		if (sparseRules.isEmpty()) {
			return false;
		}

		boolean skip = sparseRules.isEmpty() ? false : true;

		for (int i = sparseRules.size() - 1; i >= 0; i--) {
			final FastIgnoreRule sparseRule = sparseRules.get(i);

			if (sparseRule.isMatch(path
				skip = !sparseRule.getResult();
				break;
			}
		}

		return skip;
	}

	private void loadSparseCheckoutRules()
			throws FileNotFoundException
		sparseRules.clear();
		final File sparseCheckoutFile = repo.getSparseCheckoutFile();
		sparseCheckoutFileExists = FS.DETECTED.exists(sparseCheckoutFile);

		if (!isSparseCheckout || !sparseCheckoutFileExists) {
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
