		if (!isEmergencyClosing) {
			isEmergencyClosing = true;
			if (Workbench.getInstance() != null && !Workbench.getInstance().isClosing()) {
				Workbench.getInstance().close(PlatformUI.RETURN_EMERGENCY_CLOSE, true);
			}
		}

	}

	@Override
