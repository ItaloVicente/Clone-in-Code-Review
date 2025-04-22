	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		currentTransfer = transferType;
		if (currentTransfer != null && PluginTransfer.getInstance().isSupportedType(currentTransfer)) {
			return true;
		}
		return false;
	}
