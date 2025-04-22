public class CommitMessageComponentTest {

	@Test
	public void commitFormat_simple() {
		String commitMessage = "Simple message";

		String formattedMessage = CommitMessageComponent
				.formatIssuesInCommitMessage(commitMessage);
		assertEquals(null, formattedMessage);
	}

	@Test
	public void commitFormat_trailingWhitespace_ok() {
		String commitMessage = "Simple message\n\n\n";

		String formattedMessage = CommitMessageComponent
				.formatIssuesInCommitMessage(commitMessage);
		assertEquals(null, formattedMessage);
	}

	@Test
	public void commitFormat_MultipleLines_ok() {
		String commitMessage = "Simple message\n\nDetails";

		String formattedMessage = CommitMessageComponent
				.formatIssuesInCommitMessage(commitMessage);
		assertEquals(null, formattedMessage);
	}

	@Test
	public void commitFormat_MultipleLines_notOk() {
		String commitMessage = "Simple message\nDetails";

		String formattedMessage = CommitMessageComponent
				.formatIssuesInCommitMessage(commitMessage);
		assertEquals(UIText.CommitMessageComponent_MessageSecondLineNotEmpty,
				formattedMessage);
	}

	@Test
	public void commitFormat_MultipleLines_notOk2() {
		String commitMessage = "Simple message\n \nDetails";

		String formattedMessage = CommitMessageComponent
				.formatIssuesInCommitMessage(commitMessage);
		assertEquals(UIText.CommitMessageComponent_MessageSecondLineNotEmpty,
				formattedMessage);
	}

	@Test
	public void commitMessageProvider_noProvider() throws Exception {
		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				createProviderList());

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals("", calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_oneProvider() throws Exception {
		String message = "example single-line commit message";

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				createProviderList(message));

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals(message, calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_twoProviders() throws Exception {
		String message1 = "example single-line commit message";
		String message2 = "example multi-line\n\ncommit message";

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				createProviderList(message1, message2));

		String calculatedCommitMessage = commitMessageComponent
				.calculateCommitMessage(Collections.emptyList());

		assertEquals(message1 + "\n\n" + message2, calculatedCommitMessage);
	}

	@Test
	public void commitMessageProvider_oneCrashingProvider() throws Exception {

		CommitMessageComponent commitMessageComponent = newCommitMessageComponent(
				Arrays.asList(new CrashingCommitMessageProvider()));
