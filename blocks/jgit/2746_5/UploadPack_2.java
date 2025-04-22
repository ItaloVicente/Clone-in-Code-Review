	public PreUploadHook getPreUploadHook() {
		return preUploadHook;
	}

	public void setPreUploadHook(PreUploadHook hook) {
		preUploadHook = hook != null ? hook : PreUploadHook.NULL;
	}

