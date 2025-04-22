			elmtId = mhmi.getCommand() == null ? null : mhmi.getCommand()
					.getElementId();
			if (elmtId != null
					&& (elmtId.equals(COMMAND_ID_ABOUT)
							|| elmtId.equals(COMMAND_ID_PREFERENCES) || elmtId
								.equals(COMMAND_ID_QUIT))) {
