
	private ParameterizedCommand generateParameterizedCommand(final MHandledItem item) {
		Map<String, Object> parameters = null;
		List<MParameter> modelParms = item.getParameters();
		if (modelParms != null && !modelParms.isEmpty()) {
			parameters = new HashMap<String, Object>();
			for (MParameter mParm : modelParms) {
				parameters.put(mParm.getName(), mParm.getValue());
			}
		}
		ParameterizedCommand cmd = commandService.createCommand(item.getCommand().getElementId(), parameters);
		item.setWbCommand(cmd);
		return cmd;
	}
