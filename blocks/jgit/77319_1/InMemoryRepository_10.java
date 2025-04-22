							List<ReceiveCommand> cmds = getCommands();
							batch(cmds);
							for (ReceiveCommand cmd : cmds) {
								if (cmd.getResult() == ReceiveCommand.Result.OK) {
									objdb.markDirty();
									break;
								}
							}
