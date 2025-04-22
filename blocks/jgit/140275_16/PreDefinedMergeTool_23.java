	public PreDefinedMergeTool(final CommandLineMergeTool tool) {
		this(tool.name()
				tool.getParameters(false)
				BooleanOption.toConfigured(tool.isExitCodeTrustable()));
	}

