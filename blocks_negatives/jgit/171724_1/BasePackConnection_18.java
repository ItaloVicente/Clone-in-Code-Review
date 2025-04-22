				}
				if (line != null && VERSION_1.equals(line)) {
					setProtocolVersion(TransferConfig.ProtocolVersion.V0);
					isV1 = true;
					line = readLine();
				}
				if (line == null) {
					break;
				}
