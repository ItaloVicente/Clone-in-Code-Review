		preHook = new PreReceiveHook() {
			@Override
			public void onPreReceive(ReceivePack rp,
					Collection<ReceiveCommand> commands) {
				throw new IllegalStateException();
			}
