				recv.setPostReceiveHook(new PostReceiveHook() {

					@Override
					public void onPostReceive(ReceivePack rp,
							Collection<ReceiveCommand> commands) {
						packSize = rp.getPackSize();
					}
				});
