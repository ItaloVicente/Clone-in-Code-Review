	public PostUploadHook getPostUploadHook() {
		return postUploadHook;
	}

	public void setPostUploadHook(PostUploadHook hook) {
		postUploadHook = hook != null ? hook : PostUploadHook.NULL;
	}

