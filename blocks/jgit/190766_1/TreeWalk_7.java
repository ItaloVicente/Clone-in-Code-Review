	@Nullable
	public EolStreamType getCheckoutEolStreamType(int tree) {
		if (attributesNodeProvider == null || config == null) {
			return null;
		}
		Attributes attr = getAttributes(tree);
		return EolStreamTypeUtil.detectStreamType(OperationType.CHECKOUT_OP
				config.get(WorkingTreeOptions.KEY)
	}
