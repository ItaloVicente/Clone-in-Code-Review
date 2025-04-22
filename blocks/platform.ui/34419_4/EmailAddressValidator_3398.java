package org.eclipse.ui.examples.propertysheet;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class EmailAddress implements IPropertySource {

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

    private static Vector<PropertyDescriptor> descriptors;

    static {
        descriptors = new Vector<>(2, 2);
        descriptors.addElement(new PropertyDescriptor(P_ID_USERID, P_USERID));
        descriptors.addElement(new PropertyDescriptor(P_ID_DOMAIN, P_DOMAIN));
    }

    public EmailAddress() {
        super();
    }

    public EmailAddress(String emailAddress) throws IllegalArgumentException {
        super();
        setEmailAddress(emailAddress);

    }

    private static Vector<PropertyDescriptor> getDescriptors() {
        return descriptors;
    }

    private String getDomain() {
        if (domain == null)
            domain = DOMAIN_DEFAULT;
        return domain;
    }

    @Override
	public Object getEditableValue() {
        return this.toString();
    }

    @Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
        return getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    @Override
	public Object getPropertyValue(Object propKey) {
        if (propKey.equals(P_ID_USERID))
            return getUserid();
        if (propKey.equals(P_ID_DOMAIN))
            return getDomain();
        return null;
    }

    private String getUserid() {
        if (userid == null)
            userid = USERID_DEFAULT;
        return userid;
    }

    @Override
	public boolean isPropertySet(Object property) {
        return false;
    }

    @Override
	public void resetPropertyValue(Object property) {
        return;
    }

    private void setDomain(java.lang.String newDomain) {
        domain = newDomain;
    }

    private void setEmailAddress(String emailAddress)
            throws IllegalArgumentException {
        if (emailAddress == null)
            throw new IllegalArgumentException(MessageUtil
                    .getString("emailaddress_cannot_be_set_to_null")); //$NON-NLS-1$
        int index = emailAddress.indexOf('@');
        int length = emailAddress.length();
        if (index > 0 && index < length) {
            setUserid(emailAddress.substring(0, index));
            setDomain(emailAddress.substring(index + 1));
            return;
        }
        throw new IllegalArgumentException(
                MessageUtil
                        .getString("invalid_email_address_format_should_have_been_validated")); //$NON-NLS-1$
    }

    @Override
	public void setPropertyValue(Object name, Object value) {
        if (name.equals(P_ID_USERID)) {
            setUserid((String) value);
            return;
        }
        if (name.equals(P_ID_DOMAIN)) {
            setDomain((String) value);
            return;
        }
    }

    private void setUserid(String newUserid) {
        userid = newUserid;
    }

    @Override
	public String toString() {
        StringBuffer strbuffer = new StringBuffer(getUserid());
        strbuffer.append('@');
        strbuffer.append(getDomain());
        return strbuffer.toString();
    }
}
