		case DIRECTORY:
			assertTrue(t.isDirectory());
			assertTrue(t1.isDirectory());
			assertTrue(t2.isDirectory());
			break;
		case FILE:
			checkFile(t, "t");
			checkFile(t1, "t1");
			checkFile(t2, "t2");
			break;
		default:
			assertFalse(Files.exists(t.toPath(), LinkOption.NOFOLLOW_LINKS));
			assertFalse(Files.exists(t1.toPath(), LinkOption.NOFOLLOW_LINKS));
			assertFalse(Files.exists(t2.toPath(), LinkOption.NOFOLLOW_LINKS));
			break;
