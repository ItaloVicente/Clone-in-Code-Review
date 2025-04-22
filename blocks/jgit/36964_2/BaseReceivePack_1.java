			if (cmd.getType() == ReceiveCommand.Type.DELETE) {
				if (!isAllowDeletes()) {
					cmd.setResult(Result.REJECTED_NODELETE);
					continue;
				}
				if (!isAllowBranchDeletes()
						&& ref.getName().startsWith(Constants.R_HEADS)) {
					cmd.setResult(Result.REJECTED_NODELETE);
					continue;
				}
