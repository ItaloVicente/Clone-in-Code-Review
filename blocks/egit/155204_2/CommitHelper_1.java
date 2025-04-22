
		commitTemplate = config.getCommitTemplatePath()
				.map(this::getTemplateContent);
	}

	private String getTemplateContent(String templatePath) {
		Path commitTemplatePath = Paths.get(templatePath);
		if (Files.exists(commitTemplatePath)) {
			byte[] fileBytes;
			try {
				fileBytes = Files.readAllBytes(commitTemplatePath);

				String rawTemplate = new String(fileBytes).trim();
				return shouldShowCommentsInCommitTemplate()
						? rawTemplate
						: cleanTemplate(rawTemplate);
			} catch (IOException e) {
			}
		}
		return null;
	}

	public boolean shouldUseCommitTemplate() {
		return Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_MESSAGE_TEMPLATE)
				&& getCommitMessage() == null
				&& getCommitTemplate().isPresent();
	}

	public static boolean shouldShowCommentsInCommitTemplate() {
		return Activator.getDefault().getPreferenceStore().getBoolean(
				UIPreferences.COMMIT_DIALOG_MESSAGE_TEMPLATE_COMMENTS);
	}

	public static String cleanTemplate(String multiLineTemplate) {
		return multiLineTemplate.replaceAll("(?m)^#.*", "").trim(); //$NON-NLS-1$//$NON-NLS-2$
