		case DIRECTORY:
			assertTrue(new File(trash, "target").mkdir());
			assertTrue(new File(trash, "target1").mkdir());
			assertTrue(new File(trash, "target2").mkdir());
			break;
		case FILE:
			writeTrashFile("target", "t");
			writeTrashFile("target1", "t1");
			writeTrashFile("target2", "t2");
			break;
		default:
			break;
