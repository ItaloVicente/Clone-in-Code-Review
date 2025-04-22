			final StringBuilder r = new StringBuilder();
			if (forClient)
			else

			switch (cmd.getResult()) {
			case NOT_ATTEMPTED:
				break;

			case REJECTED_NOCREATE:
				break;

			case REJECTED_NODELETE:
				break;

			case REJECTED_NONFASTFORWARD:
				break;

			case REJECTED_CURRENT_BRANCH:
				break;

			case REJECTED_MISSING_OBJECT:
				if (cmd.getMessage() == null)
				else if (cmd.getMessage()
						.length() == Constants.OBJECT_ID_STRING_LENGTH) {
					r.append(cmd.getMessage());
				} else
					r.append(cmd.getMessage());
				break;

			case REJECTED_OTHER_REASON:
				if (cmd.getMessage() == null)
				else
					r.append(cmd.getMessage());
				break;

			case LOCK_FAILURE:
				break;

			case OK:
				continue;
