		PrePushHook prePush = null;
		if (local != null) {
			prePush = Hooks.prePush(local
			prePush.setRemoteLocation(uri.toString());
			prePush.setRemoteName(remoteName);
		}
		PushProcess pushProcess = new PushProcess(this
