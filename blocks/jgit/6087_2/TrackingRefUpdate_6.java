	ReceiveCommand asReceiveCommand() {
		return new Command();
	}

	final class Command extends ReceiveCommand {
		private Command() {
			super(oldObjectId
		}

		boolean canForceUpdate() {
			return forceUpdate;
		}

		@Override
		public void setResult(RefUpdate.Result status) {
			result = status;
			super.setResult(status);
		}

		@Override
		public void setResult(ReceiveCommand.Result status) {
			result = decode(status);
			super.setResult(status);
		}

		@Override
		public void setResult(ReceiveCommand.Result status
			result = decode(status);
			super.setResult(status
		}

		private RefUpdate.Result decode(ReceiveCommand.Result status) {
			switch (status) {
			case OK:
				if (AnyObjectId.equals(oldObjectId
					return RefUpdate.Result.NO_CHANGE;
				switch (getType()) {
				case CREATE:
					return RefUpdate.Result.NEW;
				case UPDATE:
					return RefUpdate.Result.FAST_FORWARD;
				case DELETE:
				case UPDATE_NONFASTFORWARD:
				default:
					return RefUpdate.Result.FORCED;
				}

			case REJECTED_NOCREATE:
			case REJECTED_NODELETE:
			case REJECTED_NONFASTFORWARD:
				return RefUpdate.Result.REJECTED;
			case REJECTED_CURRENT_BRANCH:
				return RefUpdate.Result.REJECTED_CURRENT_BRANCH;
			case REJECTED_MISSING_OBJECT:
				return RefUpdate.Result.IO_FAILURE;
			case LOCK_FAILURE:
			case NOT_ATTEMPTED:
			case REJECTED_OTHER_REASON:
			default:
				return RefUpdate.Result.LOCK_FAILURE;
			}
		}
