			case GC_REST:
				gcRestPackFound = true;
				break;
			case INSERT:
				insertPackFound = true;
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
