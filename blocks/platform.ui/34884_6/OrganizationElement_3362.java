
package org.eclipse.ui.examples.propertysheet;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class Name implements IPropertySource {
    private String firstName = ""; //$NON-NLS-1$

    private String lastName = ""; //$NON-NLS-1$

    private String initial = ""; //$NON-NLS-1$

    public static String P_ID_FIRSTNAME = "Name.FirstName"; //$NON-NLS-1$

    public static String P_ID_LASTNAME = "Name.LastName"; //$NON-NLS-1$

    public static String P_ID_MIDDLENAME = "Name.Middle"; //$NON-NLS-1$

    public static String P_FIRSTNAME = MessageUtil.getString("FirstName"); //$NON-NLS-1$

    public static String P_LASTNAME = MessageUtil.getString("LastName"); //$NON-NLS-1$

    public static String P_MIDDLENAME = MessageUtil.getString("Middle"); //$NON-NLS-1$

    private static final String FIRSTNAME_DEFAULT = null;

    private static final String LASTNAME_DEFAULT = null;

    private static final String MIDDLENAME_DEFAULT = null;

    public static final String P_DESCRIPTORS = "properties"; //$NON-NLS-1$

    static private Vector descriptors;
    static {
        descriptors = new Vector();
        descriptors.addElement(new TextPropertyDescriptor(P_ID_FIRSTNAME,
                P_FIRSTNAME));
        descriptors.addElement(new TextPropertyDescriptor(P_ID_LASTNAME,
                P_LASTNAME));
        descriptors.addElement(new TextPropertyDescriptor(P_ID_MIDDLENAME,
                P_MIDDLENAME));
    }

    public Name(String name) {
        int index1, index2;
        index1 = name.indexOf(' ');
        if (index1 < 0)
            index1 = name.length();
        index2 = name.lastIndexOf(' ');
        if (index2 > 0)
            lastName = name.substring(index2 + 1);
        firstName = name.substring(0, index1);
        if (index1 < index2)
            initial = name.substring(index1 + 1, index2);
    }

    private static Vector getDescriptors() {
        return descriptors;
    }

    public Object getEditableValue() {
        return this.toString();
    }

    private String getFirstName() {
        return firstName;
    }

    private String getInitial() {
        return initial;
    }

    private String getLastName() {
        return lastName;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return (IPropertyDescriptor[]) getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    public Object getPropertyValue(Object propKey) {
        if (P_ID_FIRSTNAME.equals(propKey))
            return getFirstName();
        if (P_ID_LASTNAME.equals(propKey))
            return getLastName();
        if (P_ID_MIDDLENAME.equals(propKey))
            return getInitial();
        return null;
    }

    public boolean isPropertySet(Object key) {
        if (key.equals(P_ID_FIRSTNAME))
            return getFirstName() != FIRSTNAME_DEFAULT;
        if (key.equals(P_ID_LASTNAME))
            return getLastName() != LASTNAME_DEFAULT;
        if (key.equals(P_ID_MIDDLENAME))
            return getInitial() != MIDDLENAME_DEFAULT;
        return false;
    }

    public void resetPropertyValue(Object property) {
        if (P_ID_FIRSTNAME.equals(property)) {
            setFirstName(FIRSTNAME_DEFAULT);
            return;
        }
        if (P_ID_LASTNAME.equals(property)) {
            setLastName(LASTNAME_DEFAULT);
            return;
        }
        if (P_ID_MIDDLENAME.equals(property)) {
            setInitial(MIDDLENAME_DEFAULT);
            return;
        }
    }

    private void setFirstName(String newFirstName) {
        firstName = newFirstName;
    }

    private void setInitial(String newInitial) {
        initial = newInitial;
    }

    private void setLastName(String newLastName) {
        lastName = newLastName;
    }

    public void setPropertyValue(Object propName, Object val) {
        if (P_ID_FIRSTNAME.equals(propName)) {
            setFirstName((String) val);
            return;
        }
        if (P_ID_LASTNAME.equals(propName)) {
            setLastName((String) val);
            return;
        }
        if (P_ID_MIDDLENAME.equals(propName)) {
            setInitial((String) val);
            return;
        }
    }

    public String toString() {
        StringBuffer outStringBuffer = new StringBuffer();
        if (getFirstName() != FIRSTNAME_DEFAULT) {
            outStringBuffer.append(getFirstName());
            outStringBuffer.append(" "); //$NON-NLS-1$
        }
        if (getInitial() != MIDDLENAME_DEFAULT) {
            outStringBuffer.append(getInitial());
            outStringBuffer.append(" "); //$NON-NLS-1$
        }
        if (getLastName() != LASTNAME_DEFAULT) {
            outStringBuffer.append(getLastName());
        }

        return outStringBuffer.toString();
    }
}
