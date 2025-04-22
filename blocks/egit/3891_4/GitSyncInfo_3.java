		if (changeType == ChangeType.MODIFY)
			return direction | CHANGE;
		if (changeType == ChangeType.ADD)
			return direction | ADDITION;
		if (changeType == ChangeType.DELETE)
			return direction | DELETION;
