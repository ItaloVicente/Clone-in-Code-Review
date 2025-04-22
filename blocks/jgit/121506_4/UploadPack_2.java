	public void setExtraParameters(Collection<String> params) {
	}

	private boolean useProtocolV2() {
		return ProtocolVersion.V2.equals(transferConfig.protocolVersion)
				&& clientRequestedV2;
	}

