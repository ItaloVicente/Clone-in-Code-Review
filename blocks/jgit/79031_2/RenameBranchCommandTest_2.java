		assertEquals(BranchRebaseMode.REBASE
				config.getEnum(BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));
		assertNull(config.getEnum(BranchRebaseMode.values()
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_REBASE
