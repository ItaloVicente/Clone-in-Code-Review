	public Transfer[] getTransfers() {
		Transfer[] types = new Transfer[listeners.size()];
		for (int i = 0; i < listeners.size(); i++) {
			TransferDropTargetListener listener = listeners
					.get(i);
			types[i] = listener.getTransfer();
		}
		return types;
	}
