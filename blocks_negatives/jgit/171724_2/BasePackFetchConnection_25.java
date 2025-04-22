			if (TransferConfig.ProtocolVersion.V2
					.equals(getProtocolVersion())) {
				sideband = true;
				noDone = true;
				multiAck = MultiAck.DETAILED;
				setFetchOptions();
				PacketLineOut output = statelessRPC ? pckState : pckOut;
				output.writeString(
				String agent = UserAgent.get();
				if (agent != null
						&& isCapableOf(GitProtocolConstants.OPTION_AGENT)) {
					output.writeString(
							GitProtocolConstants.OPTION_AGENT + '=' + agent);
				}
				output.writeDelim();
				for (String capability : getCapabilitiesV2()) {
					output.writeString(capability);
				}
			}
