			if (checkRef != null && checkRef.equals(ref)) {
				flag = true;
				break;
			}
		}

		assertTrue("Reference not enqueued", flag);
	}

	private Reference createReference(ReferenceQueue queue, Object object) {
