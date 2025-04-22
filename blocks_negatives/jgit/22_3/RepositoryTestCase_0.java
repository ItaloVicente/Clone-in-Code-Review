
		final String[] packs = {
				"pack-34be9032ac282b11fa9babdc2b2a93ca996c9c2f",
				"pack-df2982f284bbabb6bdb59ee3fcc6eb0983e20371",
				"pack-9fb5b411fe6dfa89cc2e6b89d2bd8e5de02b5745",
				"pack-546ff360fe3488adb20860ce3436a2d6373d2796",
				"pack-cbdeda40019ae0e6e789088ea0f51f164f489d14",
				"pack-e6d07037cbcf13376308a0a995d1fa48f8f76aaa",
				"pack-3280af9c07ee18a87705ef50b0cc4cd20266cf12"
		};
		final File packDir = new File(db.getObjectsDirectory(), "pack");
		for (String n : packs) {
			copyFile(JGitTestUtil.getTestResourceFile(n + ".pack"), new File(packDir, n + ".pack"));
			copyFile(JGitTestUtil.getTestResourceFile(n + ".idx"), new File(packDir, n + ".idx"));
		}

		copyFile(JGitTestUtil.getTestResourceFile("packed-refs"), new File(db
				.getDirectory(), "packed-refs"));
