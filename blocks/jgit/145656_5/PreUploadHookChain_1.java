	public static PreUploadHook newChain(List<PreUploadHook> hooks) {
		List<PreUploadHook> newHooks = hooks.stream()
				.filter(hook -> !hook.equals(PreUploadHook.NULL))
				.collect(Collectors.toList());

		if (newHooks.isEmpty()) {
