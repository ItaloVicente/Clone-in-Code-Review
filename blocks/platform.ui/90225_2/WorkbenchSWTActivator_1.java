	@Override
	public void optionsChanged(DebugOptions options) {
		trace = options.newDebugTrace(PI_RENDERERS);
		DEBUG = options.getBooleanOption(PI_RENDERERS + DEBUG_FLAG, false);
		DEBUG_MENUS = options.getBooleanOption(PI_RENDERERS + DEBUG_MENUS_FLAG, false);
		DEBUG_RENDERER = options.getBooleanOption(PI_RENDERERS + DEBUG_RENDERER_FLAG, false);
