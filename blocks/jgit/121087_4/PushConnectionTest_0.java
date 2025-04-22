						ReceivePack rp = new ReceivePack(db);
						rp.setPreReceiveHook(
								new PreReceiveHook() {
									@Override
									public void onPreReceive(ReceivePack receivePack
											Collection<ReceiveCommand> cmds) {
										for (ReceiveCommand cmd : cmds) {
											processedRefs.add(cmd.getRefName());
										}
									}
								});
						return rp;
