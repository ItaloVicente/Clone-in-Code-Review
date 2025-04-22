                    switch (d.getPackSource()) {
                        case GC:
                            gcPackFound = true;
                            assertTrue("has commit0"
                            assertFalse("no commit1"
                            break;
                        case UNREACHABLE_GARBAGE:
                            garbagePackFound = true;
                            assertFalse("no commit0"
                            assertTrue("has commit1"
                            break;
                        default:
                            fail("unexpected " + d.getPackSource());
                            break;
                    }
