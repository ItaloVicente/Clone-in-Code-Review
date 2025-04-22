
			return e;
		}

		case EMPTY:
			return new EditList(0);

		default:
			throw new IllegalStateException();
