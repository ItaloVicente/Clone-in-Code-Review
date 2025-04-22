			case GC:
				gc = pack;
				break;
			case UNREACHABLE_GARBAGE:
				garbage = pack;
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
