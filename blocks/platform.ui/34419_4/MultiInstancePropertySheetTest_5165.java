package org.eclipse.ui.tests.propertysheet;

import junit.framework.TestCase;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.views.properties.ComboBoxLabelProvider;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;

public class ComboBoxPropertyDescriptorTest extends TestCase {

    private String ID = "ID"; //$NON-NLS-1$

    private String NAME = "NAME"; //$NON-NLS-1$

    private String[] values = { "One", "Two", "Three" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    private ComboBoxPropertyDescriptor descriptor;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        descriptor = new ComboBoxPropertyDescriptor(ID, NAME, values);
    }

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetDefaultLabelProvider() {
        ILabelProvider provider = descriptor.getLabelProvider();
        assertEquals("Default label provider is of the wrong type", //$NON-NLS-1$
                ComboBoxLabelProvider.class, provider.getClass());

        for (int i = 0; i < values.length; i++) {
            String expected = values[i];
            assertEquals("Wrong label provided", //$NON-NLS-1$
                    expected, provider.getText(new Integer(i)));

        }

        testWrongLabel(provider, new Object());
        testWrongLabel(provider, null);
        testWrongLabel(provider, new Integer(-1));
        testWrongLabel(provider, new Integer(values.length));
    }

    public void testWrongLabel(ILabelProvider provider, Object element) {
        assertEquals("Wrong label provided in bad case", //$NON-NLS-1$
                "", //$NON-NLS-1$
                provider.getText(element));
    }

    public void testSetGetLabelProvider() {
        ILabelProvider provider = new LabelProvider();
        descriptor.setLabelProvider(provider);
        ILabelProvider descProvider = descriptor.getLabelProvider();
        assertSame("Wrong label provider", //$NON-NLS-1$
                provider, descProvider);
    }

}
