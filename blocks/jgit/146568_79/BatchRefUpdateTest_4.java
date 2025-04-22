		if (useReftable) {
			writeRef(name
		} else {
			write(new File(diskRepo.getDirectory()
		}
	}

	private void writeLooseRefs(String name1
			AnyObjectId id2) throws IOException {
		if (useReftable) {
			BatchRefUpdate bru = diskRepo.getRefDatabase().newBatchUpdate();

			Ref r1 = diskRepo.exactRef(name1);
			ReceiveCommand c1 = new ReceiveCommand(
					r1 != null ? r1.getObjectId() : ObjectId.zeroId()
					id1.toObjectId()

			Ref r2 = diskRepo.exactRef(name2);
			ReceiveCommand c2 = new ReceiveCommand(
					r2 != null ? r2.getObjectId() : ObjectId.zeroId()
					id2.toObjectId()

			bru.addCommand(c1
			try (RevWalk rw = new RevWalk(diskRepo)) {
				bru.execute(rw
			}
			assertEquals(c2.getResult()
			assertEquals(c1.getResult()
		} else {
			writeLooseRef(name1
			writeLooseRef(name2
		}
