			case ACCEPT_ANY:
				return Check.ALLOW;
			case ACCEPT_NEW:
				return changed ? Check.DENY : Check.ALLOW;
			default:
				return provider == null ? Check.DENY : Check.ASK;
