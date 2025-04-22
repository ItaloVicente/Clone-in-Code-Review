			initializeOutput(req);
			sendPack(pckOut
					refs == null ? null : refs.values()
					Collections.emptyList());
			if (isSideband(req)) {
				pckOut.end();
			}
