			RefUpdate u = repository.updateRef(Constants.HEAD);
			u.setRefLogMessage("checkout: moving to " + refName, false);
			switch (u.link(refName)) {
			case NEW:
			case FORCED:
			case NO_CHANGE:
				break;
			default:
				throw new IOException(u.getResult().name());
			}
