
				if (cmd.getType() == ReceiveCommand.Type.UPDATE_NONFASTFORWARD
						&& !isAllowNonFastForwards()) {
					cmd.setResult(Result.REJECTED_NONFASTFORWARD);
					continue;
				}
