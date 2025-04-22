    private String userid;

    private String domain;

    private static final String USERID_DEFAULT = MessageUtil

    private static final String DOMAIN_DEFAULT = MessageUtil





    private static ArrayList<PropertyDescriptor> descriptors;

    static {
        descriptors = new ArrayList<>();
        descriptors.add(new PropertyDescriptor(P_ID_USERID, P_USERID));
        descriptors.add(new PropertyDescriptor(P_ID_DOMAIN, P_DOMAIN));
    }

    /**
     * EmailAddress Default Constructor
     */
    public EmailAddress() {
        super();
    }

    /**
     * Convience EmailAddress constructor.
     * Calls setEmailAddress() to parse emailAddress
     * @param emailAddress java.lang.String, in the form userid@domain
     * @throws java.lang.IllegalArgumentException, if does not subscribe to form
     */
    public EmailAddress(String emailAddress) throws IllegalArgumentException {
        super();
        setEmailAddress(emailAddress);

    }

    /**
     * Returns the descriptors
     */
    private static ArrayList<PropertyDescriptor> getDescriptors() {
        return descriptors;
    }

    /**
     * Returns the domain
     */
    private String getDomain() {
        if (domain == null)
            domain = DOMAIN_DEFAULT;
        return domain;
    }

    @Override
