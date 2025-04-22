			case ISaveablePart2.YES: // yes
				break;
			case ISaveablePart2.NO: // no
				return true;
			default:
			case ISaveablePart2.CANCEL: // cancel
				return false;
