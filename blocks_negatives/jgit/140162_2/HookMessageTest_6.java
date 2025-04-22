				recv.setPreReceiveHook(new PreReceiveHook() {
					@Override
					public void onPreReceive(ReceivePack rp,
							Collection<ReceiveCommand> commands) {
						rp.sendMessage("message line 1");
						rp.sendError("no soup for you!");
						rp.sendMessage("come back next year!");
					}
				});
