			if (TransferConfig.ProtocolVersion.V2
					.equals(getProtocolVersion())) {
				pckOut.end();
				outNeedsEnd = false;
				pckOut.flush();
				return;
			}
