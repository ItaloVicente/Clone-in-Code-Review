	private String userid;

	private String domain;

	private static final String USERID_DEFAULT = MessageUtil
			.getString("unknownUser"); //$NON-NLS-1$

	private static final String DOMAIN_DEFAULT = MessageUtil
			.getString("unknownDomain"); //$NON-NLS-1$

	public static final String P_ID_USERID = "EmailAddress.userid"; //$NON-NLS-1$

	public static final String P_ID_DOMAIN = "EmailAddress.domain"; //$NON-NLS-1$

	public static final String P_USERID = MessageUtil.getString("userid"); //$NON-NLS-1$

	public static final String P_DOMAIN = MessageUtil.getString("domain"); //$NON-NLS-1$

	private static ArrayList<PropertyDescriptor> descriptors;

	static {
		descriptors = new ArrayList<>();
		descriptors.add(new PropertyDescriptor(P_ID_USERID, P_USERID));
		descriptors.add(new PropertyDescriptor(P_ID_DOMAIN, P_DOMAIN));
	}

	public EmailAddress() {
		super();
	}

	public EmailAddress(String emailAddress) throws IllegalArgumentException {
		super();
		setEmailAddress(emailAddress);

	}

	private static ArrayList<PropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	private String getDomain() {
		if (domain == null)
			domain = DOMAIN_DEFAULT;
		return domain;
	}

	@Override
