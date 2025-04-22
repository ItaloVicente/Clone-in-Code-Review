		UnitOfWork.execute(db, () -> {
			try {
				oidLength = AbbrevConfig.parseFromConfig(db).get();
			} catch (InvalidConfigurationException e) {
				Activator.logError(e.getLocalizedMessage(), e);
				oidLength = Constants.OBJECT_ID_ABBREV_STRING_LENGTH;
