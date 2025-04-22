			case GC:
				assertEquals(packSize0 + packSize1 - 32,
						d.getEstimatedPackSize());
				break;
			case UNREACHABLE_GARBAGE:
				assertEquals(packSize1, d.getEstimatedPackSize());
				break;
			default:
				fail("unexpected " + d.getPackSource());
				break;
