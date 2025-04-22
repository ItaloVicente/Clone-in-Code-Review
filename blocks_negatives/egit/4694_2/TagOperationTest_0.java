		RevTag tag = walk.parseTag(
				repository1.getRepository().resolve(tagRef.getName()));

		newTag.setMessage("Another message");
		assertFalse("Messages should differ", tag.getFullMessage().equals(
				newTag.getMessage()));
		top.execute(null);
		tag = walk.parseTag(
				repository1.getRepository().resolve(tagRef.getName()));
		assertTrue("Messages be same", tag.getFullMessage().equals(
				newTag.getMessage()));
