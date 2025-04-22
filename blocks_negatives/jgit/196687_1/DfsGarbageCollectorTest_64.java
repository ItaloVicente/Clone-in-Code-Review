			case GC:
				gcPackFound = true;
				assertTrue("has commit0", isObjectInPack(commit0, pack));
				assertFalse("no commit1", isObjectInPack(commit1, pack));
				break;
			case UNREACHABLE_GARBAGE:
				garbagePackFound = true;
				assertFalse("no commit0", isObjectInPack(commit0, pack));
				assertTrue("has commit1", isObjectInPack(commit1, pack));
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
