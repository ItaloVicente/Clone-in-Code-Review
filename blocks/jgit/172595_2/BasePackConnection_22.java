					setProtocolVersion(TransferConfig.ProtocolVersion.V0);
				} else if (!isV1 && VERSION_2.equals(line)) {
					setProtocolVersion(TransferConfig.ProtocolVersion.V2);
					readCapabilitiesV2();
					return false;
				} else {
					setProtocolVersion(TransferConfig.ProtocolVersion.V0);
				}
			} else {
				line = readLine();
				if (line == null) {
					break;
