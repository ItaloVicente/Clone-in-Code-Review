		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				ContextMenuHelper.clickContextMenuSync(projectExplorerTree,
						menuPath);
			}
		});

		SWTBotText developText = bot.textWithLabel(InitDialog_developBranch);
		developText.selectAll();
		developText.typeText(ILLEGAL_BRANCH_NAME);


		SWTBotButton ok = bot.button("OK");
		assertFalse(ok.isEnabled());

		typeInto(InitDialog_developBranch, DEVELOP_BRANCH);
		typeInto(InitDialog_masterBranch, MASTER_BRANCH);
		typeInto(InitDialog_featureBranchPrefix, FEATURE_BRANCH_PREFIX);
		typeInto(InitDialog_releaseBranchPrefix, RELEASE_BRANCH_PREFIX);
		typeInto(InitDialog_hotfixBranchPrefix, HOTFIX_BRANCH_PREFIX);
		typeInto(InitDialog_versionTagPrefix, VERSION_TAG_PREFIX);

		ok.click();
