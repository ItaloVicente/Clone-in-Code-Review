				case GC:
					gcPackFound = true;
					assertTrue(isObjectInPack(commit0
					assertFalse(isObjectInPack(commit1
					break;
				case UNREACHABLE_GARBAGE:
					garbagePackFound = true;
					assertFalse(isObjectInPack(commit0
					assertTrue(isObjectInPack(commit1
					break;
				default:
					fail("unexpected " + d.getPackSource());
					break;
