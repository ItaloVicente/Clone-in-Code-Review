
package org.eclipse.core.tests.databinding.observable.value;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.databinding.observable.value.ComputedValue;
import org.eclipse.core.databinding.observable.value.WritableValue;
import org.eclipse.jface.tests.databinding.AbstractDefaultRealmTestCase;

public class ComputedValueTest extends AbstractDefaultRealmTestCase {
    public void testValueType() throws Exception {
        ComputedValue cv = new ComputedValue(Integer.TYPE) {
            @Override
			protected Object calculate() {
                return new Integer(42);
            }
        };
        assertEquals("value type should be the type that was set", Integer.TYPE, cv.getValueType());

        cv = new ComputedValue() {
            @Override
			protected Object calculate() {
                return null;
            }
        };

        assertNull(cv.getValueType());
    }

    public void test_getValue() throws Exception {
        ComputedValue cv = new ComputedValue() {
            @Override
			protected Object calculate() {
                return new Integer(42);
            }
        };
        assertEquals("Calculated value should be 42", new Integer(42), cv.getValue());
    }

    public void testDependencyValueChange() throws Exception {
        final WritableValue value = new WritableValue(new Integer(42), Integer.TYPE);

        ComputedValue cv = new ComputedValue() {
            @Override
			protected Object calculate() {
                return value.getValue();
            }
        };

        assertEquals("calculated value should have been that of the writable value", value.getValue(), cv.getValue());

        value.setValue(new Integer(44));

        assertEquals("calculated value should have been that of the writable value", value.getValue(), cv.getValue());
    }

    private static class WritableValueExt extends WritableValue {
        public WritableValueExt(Object valueType, Object initialValue) {
            super(initialValue, valueType);
        }

        @Override
		public boolean hasListeners() {
            return super.hasListeners();
        }
    }

    public void testHookAndUnhookDependantObservables() throws Exception {
        final List values = new ArrayList();

        ComputedValue cv = new ComputedValue() {
            @Override
			protected Object calculate() {
                int sum = 0;
                for (Iterator it = values.iterator(); it.hasNext();) {
                    WritableValue value = (WritableValue) it.next();
                    sum += ((Integer) value.getValue()).intValue();

                }

                return new Integer(sum);
            }
        };

        WritableValueExt value1 = new WritableValueExt(Integer.TYPE, new Integer(1));
        WritableValueExt value2 = new WritableValueExt(Integer.TYPE, new Integer(1));
        values.add(value1);
        values.add(value2);
        
        assertFalse(value1.hasListeners());
        assertFalse(value2.hasListeners());
        cv.getValue();
        assertTrue(value1.hasListeners());
        assertTrue(value2.hasListeners());
        
        value2.setValue(new Integer(2));
        values.remove(value2);
        
        cv.getValue();
        assertEquals(new Integer(1), cv.getValue());
        assertTrue(value1.hasListeners());
        assertFalse("because value2 is not a part of the calculation the listeners should have been removed", value2.hasListeners());
    }
    
    public void testSetValueUnsupportedOperationException() throws Exception {
        ComputedValue cv = new ComputedValue() {
            @Override
			protected Object calculate() {
                return null;
            }
        };
        
        try {
            cv.setValue(new Object());
            fail("exception should have been thrown");
        } catch (UnsupportedOperationException e) {
        }
    }
}
