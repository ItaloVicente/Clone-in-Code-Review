		String ACTIVITY = "<plugin><extension point=\"org.eclipse.ui.activities\">"
				+ "<category id=\"dynamic.category\" name=\"Dynamic Activity Category\"/>"
				+ "<activity id=\"dynamic.activity\" name=\"Dynamic Activity\"/>"
				+ "<activity id=\"dynamic.parent\" name=\"Dynamic Parent Activity\"/>"
				+ "<activityRequirementBinding requiredActivityId = \"dynamic.parent\" activityId = \"dynamic.activity\" />"
				+ "<categoryActivityBinding categoryId = \"dynamic.category\" activityId = \"dynamic.activity\" />"
				+ "<activityPatternBinding activityId=\"dynamic.activity\"  pattern=\"dynamic.activity/.*\"/>"
				+ "<defaultEnablement id=\"dynamic.activity\"/>" + "</extension></plugin>";
		byte[] bytes = ACTIVITY.toString().getBytes(StandardCharsets.UTF_8);
		InputStream is = new ByteArrayInputStream(bytes);
		IContributor contrib = ContributorFactoryOSGi.createContributor(TestPlugin.getDefault().getBundle());
		ExtensionRegistry registry = (ExtensionRegistry) RegistryFactory.getRegistry();
		if (!registry.addContribution(is, contrib, false, null, null, registry.getTemporaryUserToken())) {
			throw new RuntimeException();
