
	public boolean isForCheckout(String path) {
		boolean isForCheckout = sparseCheckoutRules.isEmpty() ? true
				: false;

		for (FastIgnoreRule sparseCheckoutRule : sparseCheckoutRules) {
			if (sparseCheckoutRule.isMatch(path
				if (sparseCheckoutRule.getResult()) {
					isForCheckout = true;
				} else {
					isForCheckout = false;
					break;
				}
			}
		}


		return isForCheckout;
	}

	private void loadSparseCheckoutRules()
			throws FileNotFoundException
		StoredConfig rc = repo.getConfig();
		final boolean isSparseCheckoutEnabled = rc.getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SPARSECHECKOUT

		final File sparseCheckoutFile = repo.getSparseCheckoutFile();

		if (isSparseCheckoutEnabled && FS.DETECTED.exists(sparseCheckoutFile)) {
			FileInputStream in = new FileInputStream(sparseCheckoutFile);

			try {
				String txt;

				BufferedReader br = new BufferedReader(
						new InputStreamReader(in

				try {
					while ((txt = br.readLine()) != null) {
							FastIgnoreRule rule = new FastIgnoreRule(txt);

							if (!rule.isEmpty()) {
								sparseCheckoutRules.add(rule);
							}
						}
					}
				} finally {
					br.close();
				}
			} finally {
				in.close();
			}
		}
	}

