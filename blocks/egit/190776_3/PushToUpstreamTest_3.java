		JobJoiner joiner = null;
		if (!expectBranchWizard) {
			joiner = JobJoiner.startListening(JobFamilies.PUSH, 20,
					TimeUnit.SECONDS);
		}
		ContextMenuHelper.clickContextMenu(project,
				getPushToUpstreamMenuPath(remoteName));
		if (expectBranchWizard) {
			PushBranchWizardTester tester = PushBranchWizardTester
					.forBranchName(branchName);
			tester.next();
			TestUtil.openJobResultDialog(tester.finish());
		} else if (expectMultipleWarning) {
			SWTBot dialog = bot.shell(UIText.PushOperationUI_PushMultipleTitle)
					.bot();
			dialog.button(UIText.PushOperationUI_PushMultipleOkLabel).click();
		}
		if (joiner != null) {
			TestUtil.openJobResultDialog(joiner.join());
		}
