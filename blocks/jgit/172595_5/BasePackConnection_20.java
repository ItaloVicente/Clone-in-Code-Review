			if (first) {
				boolean isV1 = false;
				try {
					line = readLine();
				} catch (EOFException e) {
					TransportException noRepo = noRepository();
					noRepo.initCause(e);
					throw noRepo;
				}
				if (line != null && VERSION_1.equals(line)) {
					setProtocolVersion(TransferConfig.ProtocolVersion.V0);
					isV1 = true;
					line = readLine();
				}
				if (line == null) {
					break;
				}
