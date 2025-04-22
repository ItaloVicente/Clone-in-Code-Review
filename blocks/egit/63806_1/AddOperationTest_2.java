		assertEquals(file1.getLocalTimeStamp() / 1000,
				testRepository.lastModifiedInIndex(
						file1.getLocation().toPortableString()) / 1000);
		assertEquals(file2.getLocalTimeStamp() / 1000,
				testRepository.lastModifiedInIndex(
						file2.getLocation().toPortableString()) / 1000);
