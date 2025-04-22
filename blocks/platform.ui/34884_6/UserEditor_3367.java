package org.eclipse.ui.examples.propertysheet;

import java.util.Vector;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class StreetAddress implements IPropertySource {

    private Integer buildNo;

    private String aptBox;

    private String streetName;

    private static final Integer BUILD_NO_DEFAULT = new Integer(0);

    private static final String APTBOX_DEFAULT = MessageUtil
            .getString("unspecified"); //$NON-NLS-1$

    private static final String STREETNAME_DEFAULT = MessageUtil
            .getString("unspecified"); //$NON-NLS-1$

    public static final String P_ID_BUILD_NO = "Street.buildingNo"; //$NON-NLS-1$

    public static final String P_ID_APTBOX = "Street.aptNo"; //$NON-NLS-1$

    public static final String P_ID_STREET = "Street.street"; //$NON-NLS-1$

    public static final String P_BUILD_NO = MessageUtil
            .getString("building_number"); //$NON-NLS-1$

    public static final String P_APTBOX = MessageUtil
            .getString("apt.no_or_box.no"); //$NON-NLS-1$

    public static final String P_STREET = MessageUtil.getString("street"); //$NON-NLS-1$

    private static Vector descriptors;

    static {
        descriptors = new Vector();
        descriptors.addElement(new TextPropertyDescriptor(P_ID_BUILD_NO,
                P_BUILD_NO));
        descriptors
                .addElement(new TextPropertyDescriptor(P_ID_APTBOX, P_APTBOX));
        descriptors
                .addElement(new TextPropertyDescriptor(P_ID_STREET, P_STREET));
    }

    public StreetAddress() {
        super();
    }

    public StreetAddress(int buildNo, String streetName) {
        super();
        setBuildNo(new Integer(buildNo));
        setStreetName(streetName);
    }

    public StreetAddress(int buildNo, String aptBox, String streetName) {
        super();
        setBuildNo(new Integer(buildNo));
        setAptBox(aptBox);
        setStreetName(streetName);
    }

    public boolean equals(Object ob) {
        return toString().equals(ob.toString());
    }

    private String getAptBox() {
        if (aptBox == null)
            aptBox = APTBOX_DEFAULT;
        return aptBox;
    }

    private Integer getBuildNo() {
        if (buildNo == null)
            buildNo = BUILD_NO_DEFAULT;
        return buildNo;
    }

    private static Vector getDescriptors() {
        return descriptors;
    }

    public Object getEditableValue() {
        return this.toString();
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return (IPropertyDescriptor[]) getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    public Object getPropertyValue(Object propKey) {
        if (propKey.equals(P_ID_BUILD_NO))
            return getBuildNo().toString();
        if (propKey.equals(P_ID_APTBOX))
            return getAptBox();
        if (propKey.equals(P_ID_STREET))
            return getStreetName();
        return null;
    }

    private String getStreetName() {
        if (streetName == null)
            streetName = STREETNAME_DEFAULT;
        return streetName;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean isPropertySet(Object property) {
        if (property.equals(P_ID_BUILD_NO))
            return getBuildNo() != BUILD_NO_DEFAULT;
        if (property.equals(P_ID_APTBOX))
            return getAptBox() != APTBOX_DEFAULT;
        if (property.equals(P_ID_STREET))
            return getStreetName() != STREETNAME_DEFAULT;
        return false;
    }

    public void resetPropertyValue(Object property) {
        if (property.equals(P_ID_BUILD_NO)) {
            setBuildNo(BUILD_NO_DEFAULT);
            return;
        }
        if (property.equals(P_ID_APTBOX)) {
            setAptBox(APTBOX_DEFAULT);
            return;
        }
        if (property.equals(P_ID_STREET)) {
            setStreetName(STREETNAME_DEFAULT);
            return;
        }
    }

    private void setAptBox(String newAptBox) {
        aptBox = newAptBox;
    }

    private void setBuildNo(Integer newBuildNo) {
        buildNo = newBuildNo;
    }

    public void setPropertyValue(Object name, Object value) {
        if (name.equals(P_ID_BUILD_NO)) {
            try {
                setBuildNo(new Integer(Integer.parseInt((String) value)));
            } catch (NumberFormatException e) {
                setBuildNo(BUILD_NO_DEFAULT);
            }
            return;
        }
        if (name.equals(P_ID_APTBOX)) {
            setAptBox((String) value);
            return;
        }
        if (name.equals(P_ID_STREET)) {
            setStreetName((String) value);
            return;
        }
    }

    private void setStreetName(String newStreetName) {
        streetName = newStreetName;
    }

    public String toString() {
        StringBuffer outStringBuffer = new StringBuffer();
        if (!getAptBox().equals(APTBOX_DEFAULT)) {
            outStringBuffer.append(getAptBox());
            outStringBuffer.append(", "); //$NON-NLS-1$
        }
        if (!getBuildNo().equals(BUILD_NO_DEFAULT)) {
            outStringBuffer.append(getBuildNo());
            outStringBuffer.append(" "); //$NON-NLS-1$
        }
        if (!getStreetName().equals(STREETNAME_DEFAULT)) {
            outStringBuffer.append(getStreetName());
        }
        return outStringBuffer.toString();
    }
}
