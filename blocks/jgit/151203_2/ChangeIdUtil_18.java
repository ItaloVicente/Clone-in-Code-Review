			StringBuilder ret = new StringBuilder(
					message.substring(0
			ret.append(CHANGE_ID);
			ret.append(ObjectId.toString(changeId));
			int indexOfNextLineBreak = message.indexOf("\n"
					indexOfChangeId);
			if (indexOfNextLineBreak > 0)
				ret.append(message.substring(indexOfNextLineBreak));
			return ret.toString();
