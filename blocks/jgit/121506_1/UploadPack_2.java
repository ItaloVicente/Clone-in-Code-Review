	public void setExtraParameters(Collection<String> params) {
	}

	private boolean useProtocolV2() {
		return Objects.equals(transferConfig.protocolVersion
					ProtocolVersion.V2)
				&& clientRequestedV2;
	}

