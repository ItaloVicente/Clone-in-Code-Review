	@Override
	public void optionsChanged(DebugOptions options) {
		trace = options.newDebugTrace(PI_WORKBENCH);
		DEBUG = options.getBooleanOption(PI_WORKBENCH + DEBUG_FLAG, false);
		DEBUG_CMDS = options.getBooleanOption(PI_WORKBENCH + DEBUG_CMDS_FLAG, false);
		DEBUG_CONTEXTS = options.getBooleanOption(PI_WORKBENCH + DEBUG_CONTEXTS_FLAG, false);
		DEBUG_CONTEXTS_VERBOSE = options.getBooleanOption(PI_WORKBENCH + DEBUG_CONTEXTS_VERBOSE_FLAG, false);
		DEBUG_MENUS = options.getBooleanOption(PI_WORKBENCH + DEBUG_MENUS_FLAG, false);
		DEBUG_RENDERER = options.getBooleanOption(PI_WORKBENCH + DEBUG_RENDERER_FLAG, false);
		DEBUG_WORKBENCH = options.getBooleanOption(PI_WORKBENCH + DEBUG_WORKBENCH_FLAG, false);
