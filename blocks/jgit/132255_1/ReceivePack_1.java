					preReceive.onPreReceive(
							this
					if (atomic && anyRejects()) {
						failPendingCommands();
					}
					executeCommands();
				}
			} finally {
				unlockPack();
