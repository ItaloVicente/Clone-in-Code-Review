			switch (newAction) {
			case SKIP:
				line.setAction(Action.COMMENT);
				break;
			case EDIT:
				line.setAction(Action.EDIT);
				break;
			case FIXUP:
				line.setAction(Action.FIXUP);
				break;
			case PICK:
				line.setAction(Action.PICK);
				break;
			case REWORD:
				line.setAction(Action.REWORD);
				break;
			case SQUASH:
				line.setAction(Action.SQUASH);
				break;
			default:
				throw new IllegalArgumentException();
