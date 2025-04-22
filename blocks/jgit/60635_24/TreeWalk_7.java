	public @Nullable EolStreamType getEolStreamType() {
			if (attributesNodeProvider == null || config == null)
				return null;
			return EolStreamTypeUtil.detectStreamType(operationType
					config.get(WorkingTreeOptions.KEY)
	}

