    private QName name;
    private Object bean;
    private JXPathEObjectInfo beanInfo;

    private static final long serialVersionUID = -8227317938284982440L;

    /**
     * Create a new BeanPointer.
     * @param name is the name given to the first node
     * @param bean pointed
     * @param beanInfo JXPathBeanInfo
     * @param locale Locale
     */
    public EObjectPointer(QName name, Object bean, JXPathEObjectInfo beanInfo,
            Locale locale) {
        super(null, locale);
        this.name = name;
        this.bean = bean;
        this.beanInfo = beanInfo;
    }

    /**
     * Create a new BeanPointer.
     * @param parent pointer
     * @param name is the name given to the first node
     * @param bean pointed
     * @param beanInfo JXPathBeanInfo
     */
    public EObjectPointer(NodePointer parent, QName name, Object bean,
    		JXPathEObjectInfo beanInfo) {
        super(parent);
        this.name = name;
        this.bean = bean;
        this.beanInfo = beanInfo;
    }

    @Override
