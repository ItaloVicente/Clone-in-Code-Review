		private void reject(List<ReceiveCommand> cmds) {
			for (ReceiveCommand c : cmds) {
				if (c.getResult() == ReceiveCommand.Result.NOT_ATTEMPTED) {
					c.setResult(ReceiveCommand.Result.REJECTED_OTHER_REASON,
							JGitText.get().transactionAborted);
				}
			}
		}

