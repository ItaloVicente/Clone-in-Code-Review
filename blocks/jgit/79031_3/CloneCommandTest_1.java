		assertEquals(BranchRebaseMode.REBASE
				git2.getRepository().getConfig().getEnum(
						BranchRebaseMode.values()
						ConfigConstants.CONFIG_BRANCH_SECTION
						ConfigConstants.CONFIG_KEY_REBASE
						BranchRebaseMode.NONE));
