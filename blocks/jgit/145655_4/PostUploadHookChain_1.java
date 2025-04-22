	public static PostUploadHook newChain(List<PostUploadHook> hooks) {
		List<PostUploadHook> newHooks = hooks.stream()
				.filter(hook -> !hook.equals(PostUploadHook.NULL))
				.collect(Collectors.toList());

		if (newHooks.isEmpty()) {
