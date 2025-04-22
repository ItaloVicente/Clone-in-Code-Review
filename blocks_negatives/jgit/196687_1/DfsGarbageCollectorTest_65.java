			case GC:
				gcPackFound = true;
				break;
			case INSERT:
				insertPackFound = true;
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
