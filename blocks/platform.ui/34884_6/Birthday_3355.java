package org.eclipse.ui.examples.propertysheet;

import java.util.Vector;

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

public class Address implements IPropertySource {

    private String city;

    private Integer province;

    private String postalCode;

    private StreetAddress street;

    public static final String P_ID_STREET = "Address.Street"; //$NON-NLS-1$

    public static final String P_ID_CITY = "Address.City"; //$NON-NLS-1$

    public static final String P_ID_PROVINCE = "Address.Province"; //$NON-NLS-1$

    public static final String P_ID_POSTALCODE = "Address.PostalCode"; //$NON-NLS-1$

    public static final String P_STREET = MessageUtil.getString("Street"); //$NON-NLS-1$

    public static final String P_CITY = MessageUtil.getString("City"); //$NON-NLS-1$

    public static final String P_PROVINCE = MessageUtil.getString("Province"); //$NON-NLS-1$

    public static final String P_POSTALCODE = MessageUtil
            .getString("PostalCode"); //$NON-NLS-1$

    public static final String P_DESCRIPTORS = "properties"; //$NON-NLS-1$

    private static final StreetAddress STREET_DEFAULT = new StreetAddress();

    private static final String CITY_DEFAULT = MessageUtil
            .getString("unspecified_city"); //$NON-NLS-1$

    private static final Integer PROVINCE_DEFAULT = new Integer(0);

    private static final String POSTALCODE_DEFAULT = "A1B2C3"; //$NON-NLS-1$

    static private class ProvinceLabelProvider extends LabelProvider {
        public String getText(Object element) {
            String[] provinceValues = new String[] {
                    MessageUtil.getString("British_Columbia"), MessageUtil.getString("Alberta"), MessageUtil.getString("Saskatchewan"), MessageUtil.getString("Manitoba"), MessageUtil.getString("Ontario"), MessageUtil.getString("Quebec"), MessageUtil.getString("Newfoundland"), MessageUtil.getString("Prince_Edward_Island"), MessageUtil.getString("Nova_Scotia"), MessageUtil.getString("New_Brunswick"), MessageUtil.getString("Yukon"), MessageUtil.getString("North_West_Territories"), MessageUtil.getString("Nunavut") }; //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
            return provinceValues[((Integer) element).intValue()];
        }
    }

    private static Vector descriptors;

    private static String[] provinceValues;
    static {
        descriptors = new Vector();
        provinceValues = new String[] {
                MessageUtil.getString("British_Columbia"), MessageUtil.getString("Alberta"), MessageUtil.getString("Saskatchewan"), MessageUtil.getString("Manitoba"), MessageUtil.getString("Ontario"), MessageUtil.getString("Quebec"), MessageUtil.getString("Newfoundland"), MessageUtil.getString("Prince_Edward_Island"), MessageUtil.getString("Nova_Scotia"), MessageUtil.getString("New_Brunswick"), MessageUtil.getString("Yukon"), MessageUtil.getString("North_West_Territories"), MessageUtil.getString("Nunavut") }; //$NON-NLS-13$ //$NON-NLS-12$ //$NON-NLS-11$ //$NON-NLS-10$ //$NON-NLS-9$ //$NON-NLS-8$ //$NON-NLS-7$ //$NON-NLS-6$ //$NON-NLS-5$ //$NON-NLS-4$ //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
        descriptors.addElement(new PropertyDescriptor(P_ID_STREET, P_STREET));
        descriptors.addElement(new TextPropertyDescriptor(P_ID_CITY, P_CITY));

        PropertyDescriptor propertyDescriptor = new TextPropertyDescriptor(
                P_ID_POSTALCODE, P_POSTALCODE);
        propertyDescriptor.setValidator(new ICellEditorValidator() {
            public String isValid(Object value) {
                if (value == null)
                    return MessageUtil.getString("postal_code_is_incomplete"); //$NON-NLS-1$

                String testPostalCode = ((String) value).toUpperCase();
                final int length = testPostalCode.length();
                final char space = ' ';

                StringBuffer postalCodeBuffer = new StringBuffer(6);
                char current;
                for (int i = 0; i < length; i++) {
                    current = testPostalCode.charAt(i);
                    if (current != space)
                        postalCodeBuffer.append(current);
                }
                testPostalCode = postalCodeBuffer.toString();

                if (testPostalCode.length() != 6) {
                    return MessageUtil.getString("postal_code_is_incomplete"); //$NON-NLS-1$
                }

                if (testPostalCode.charAt(1) < '0'
                        || testPostalCode.charAt(1) > '9'
                        || testPostalCode.charAt(3) < '0'
                        || testPostalCode.charAt(3) > '9'
                        || testPostalCode.charAt(5) < '0'
                        || testPostalCode.charAt(5) > '9'
                        || testPostalCode.charAt(0) < 'A'
                        || testPostalCode.charAt(0) > 'Z'
                        || testPostalCode.charAt(2) < 'A'
                        || testPostalCode.charAt(2) > 'Z'
                        || testPostalCode.charAt(4) < 'A'
                        || testPostalCode.charAt(4) > 'Z') {
                    return MessageUtil
                            .format(
                                    "_is_an_invalid_format_for_a_postal_code", new Object[] { testPostalCode }); //$NON-NLS-1$
                }

                return null;
            }
        });
        descriptors.addElement(propertyDescriptor);

        ComboBoxPropertyDescriptor desc = new ComboBoxPropertyDescriptor(
                P_ID_PROVINCE, P_PROVINCE, provinceValues);
        desc.setLabelProvider(new ProvinceLabelProvider());
        descriptors.addElement(desc);
    }

    Address() {
        super();
    }

    public Address(StreetAddress street, String city, Integer province,
            String postalCode) {
        super();
        setStreet(street);
        setCity(city);
        setPostalCode(postalCode);
        setProvince(province);
    }

    private String getCity() {
        if (city == null)
            city = CITY_DEFAULT;
        return city;
    }

    private static Vector getDescriptors() {
        return descriptors;
    }

    public Object getEditableValue() {
        return this.toString();
    }

    private String getPostalCode() {
        if (postalCode == null)
            postalCode = POSTALCODE_DEFAULT;
        return postalCode;
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return (IPropertyDescriptor[]) getDescriptors().toArray(
                new IPropertyDescriptor[getDescriptors().size()]);
    }

    public Object getPropertyValue(Object propKey) {
        if (propKey.equals(P_ID_PROVINCE))
            return getProvince();
        if (propKey.equals(P_ID_STREET))
            return getStreet();
        if (propKey.equals(P_ID_CITY))
            return getCity();
        if (propKey.equals(P_ID_POSTALCODE))
            return getPostalCode();
        return null;
    }

    private Integer getProvince() {
        if (province == null)
            province = PROVINCE_DEFAULT;
        return province;
    }

    public StreetAddress getStreet() {
        if (street == null)
            street = new StreetAddress();
        return street;
    }

    public boolean isPropertySet(Object property) {
        if (property.equals(P_ID_PROVINCE))
            return getProvince() != PROVINCE_DEFAULT;
        if (property.equals(P_ID_STREET))
            return !STREET_DEFAULT.equals(getStreet());
        if (property.equals(P_ID_CITY))
            return getCity() != CITY_DEFAULT;
        if (property.equals(P_ID_POSTALCODE))
            return getPostalCode() != POSTALCODE_DEFAULT;
        return false;
    }

    public void resetPropertyValue(Object property) {
        if (P_ID_POSTALCODE.equals(property)) {
            setPostalCode(POSTALCODE_DEFAULT);
            return;
        }
        if (P_ID_CITY.equals(property)) {
            setCity(CITY_DEFAULT);
            return;
        }
        if (P_ID_PROVINCE.equals(property)) {
            setProvince(PROVINCE_DEFAULT);
            return;
        }
        if (P_ID_STREET.equals(property)) {
            setStreet(new StreetAddress());
            return;
        }
    }

    private void setCity(String newCity) {
        city = newCity;
    }

    private void setPostalCode(String newPostalCode) {
        this.postalCode = newPostalCode.toUpperCase();
    }

    public void setPropertyValue(Object name, Object value) {
        if (P_ID_POSTALCODE.equals(name)) {
            setPostalCode((String) value);
            return;
        }
        if (P_ID_CITY.equals(name)) {
            setCity((String) value);
            return;
        }
        if (P_ID_PROVINCE.equals(name)) {
            setProvince((Integer) value);
            return;
        }
    }

    private void setProvince(Integer newProvince) {
        province = newProvince;
    }

    private void setStreet(StreetAddress newStreet) {
        street = newStreet;
    }

    public String toString() {
        StringBuffer outStringBuffer = new StringBuffer();
        final String comma_space = ", "; //$NON-NLS-1$
        final String space = " "; //$NON-NLS-1$
        if (!getStreet().equals(STREET_DEFAULT)) {
            outStringBuffer.append(getStreet());
            outStringBuffer.append(comma_space);
        }

        outStringBuffer.append(getCity());
        outStringBuffer.append(space);
        outStringBuffer.append(provinceValues[getProvince().intValue()]);
        outStringBuffer.append(comma_space);
        outStringBuffer.append(getPostalCode());

        return outStringBuffer.toString();
    }
}
