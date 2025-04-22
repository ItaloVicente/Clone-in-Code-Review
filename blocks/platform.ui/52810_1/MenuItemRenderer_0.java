	private ParameterizedCommand generateParameterizedCommand(final MHandledItem item,
			final IEclipseContext lclContext) {
		ECommandService cmdService = (ECommandService) lclContext.get(ECommandService.class.getName());
		Map<String, Object> parameters = null;
		List<MParameter> modelParms = item.getParameters();
		if (modelParms != null && !modelParms.isEmpty()) {
			parameters = new HashMap<String, Object>();
			for (MParameter mParm : modelParms) {
				parameters.put(mParm.getName(), mParm.getValue());
			}
		}
		ParameterizedCommand cmd = cmdService.createCommand(item.getCommand().getElementId(), parameters);
		item.setWbCommand(cmd);
		return cmd;
	}

