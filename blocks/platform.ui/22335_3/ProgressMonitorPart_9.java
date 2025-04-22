				return escapeMetaCharacters(JFaceResources
						.format("Set_SubTask", new Object[] { fTaskName, fSubTaskName }));//$NON-NLS-1$
			return escapeMetaCharacters(fTaskName);

		} else if (hasSubtask) {
			return escapeMetaCharacters(fSubTaskName);

		} else {
			return ""; //$NON-NLS-1$
		}
	}

	@Override
