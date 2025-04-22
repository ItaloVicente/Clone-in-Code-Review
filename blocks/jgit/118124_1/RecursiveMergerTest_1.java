			break;
		case SameAsOther:
			try (FileOutputStream fos = new FileOutputStream(
					new File(db.getWorkTree()
				db.newObjectReader().open(contentId(other
			}
			break;
		case Bare:
			if (db.isBare())
				return;
			File workTreeFile = db.getWorkTree();
			db.getConfig().setBoolean("core"
			db.getDirectory().renameTo(new File(workTreeFile
			db = new FileRepository(new File(workTreeFile
			db_t = new TestRepository<>(db);
