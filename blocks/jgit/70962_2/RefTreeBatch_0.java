					if (isAtomic()) {
						ReceiveCommand.abort(getCommands());
						return;
					} else {
						continue;
					}
