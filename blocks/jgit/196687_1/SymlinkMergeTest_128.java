			case DIRECTORY:
				assertTrue(t.isDirectory());
				assertTrue(t1.isDirectory());
				assertTrue(t2.isDirectory());
				break;
			case FILE:
				checkFile(t
				checkFile(t1
				checkFile(t2
				break;
			default:
				assertFalse(Files.exists(t.toPath()
				assertFalse(Files.exists(t1.toPath()
				assertFalse(Files.exists(t2.toPath()
				break;
