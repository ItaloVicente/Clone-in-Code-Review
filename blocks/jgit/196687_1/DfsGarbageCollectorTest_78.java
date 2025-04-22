				case GC:
					gcPackFound = true;
					assertEquals(gcPackSize + insertPackSize - 32
							pack.getPackDescription().getEstimatedPackSize());
					break;
				case GC_REST:
					gcRestPackFound = true;
					assertEquals(gcRestPackSize + insertPackSize - 32
							pack.getPackDescription().getEstimatedPackSize());
					break;
				default:
					fail("unexpected " + d.getPackSource());
					break;
