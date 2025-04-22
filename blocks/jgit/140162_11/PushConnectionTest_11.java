		testProtocol = new TestProtocol<>(null
			ReceivePack rp = new ReceivePack(db);
			rp.setPreReceiveHook((ReceivePack receivePack
					Collection<ReceiveCommand> cmds) -> {
				for (ReceiveCommand cmd : cmds) {
					processedRefs.add(cmd.getRefName());
				}
			});
			return rp;
		});
