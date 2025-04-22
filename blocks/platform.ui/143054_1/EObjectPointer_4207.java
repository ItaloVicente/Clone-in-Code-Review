	private QName name;
	private Object bean;
	private JXPathEObjectInfo beanInfo;

	private static final long serialVersionUID = -8227317938284982440L;

	public EObjectPointer(QName name, Object bean, JXPathEObjectInfo beanInfo,
			Locale locale) {
		super(null, locale);
		this.name = name;
		this.bean = bean;
		this.beanInfo = beanInfo;
	}

	public EObjectPointer(NodePointer parent, QName name, Object bean,
			JXPathEObjectInfo beanInfo) {
		super(parent);
		this.name = name;
		this.bean = bean;
		this.beanInfo = beanInfo;
	}

	@Override
