			case GC:
				gcPackFound = true;
				gcPackSize = d.getFileSize(PACK);
				break;
			case GC_REST:
				gcRestPackFound = true;
				gcRestPackSize = d.getFileSize(PACK);
				break;
			case INSERT:
				insertPackFound = true;
				insertPackSize = d.getFileSize(PACK);
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
