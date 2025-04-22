	public static final String UIRendererTopicBase = UITopicBase + "/renderer"; //$NON-NLS-1$

	public static final String REQUEST_ENABLEMENT_UPDATE_TOPIC = UIRendererTopicBase
			+ "/requestEnablementUpdate"; //$NON-NLS-1$

	public interface Selector {
		public boolean select(MApplicationElement element);
	}

	public static final String ALL_ELEMENT_ID = "ALL"; //$NON-NLS-1$

