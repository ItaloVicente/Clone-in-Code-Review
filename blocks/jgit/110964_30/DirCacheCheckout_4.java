		this.sparseRules = new ArrayList<>();
		this.isSparseCheckout = repo.getConfig().getBoolean(
				ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_SPARSECHECKOUT
		this.sparseCheckoutFileExists = FS.DETECTED
				.exists(repo.getSparseCheckoutFile());
