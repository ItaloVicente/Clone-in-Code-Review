			RefUpdate u = refdb.newUpdate(Constants.HEAD
			u.disableRefLog();
			switch (u.link(target.getName())) {
			case NEW:
			case FORCED:
			case NO_CHANGE:
				return true;
			default:
				return false;
			}
