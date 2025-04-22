                int sum = 0;
                for (Iterator it = values.iterator(); it.hasNext();) {
                    WritableValue value = (WritableValue) it.next();
                    sum += ((Integer) value.getValue()).intValue();

                }

                return Integer.valueOf(sum);
            }
        };

        WritableValueExt value1 = new WritableValueExt(Integer.TYPE, Integer.valueOf(1));
        WritableValueExt value2 = new WritableValueExt(Integer.TYPE, Integer.valueOf(1));
        values.add(value1);
        values.add(value2);

        assertFalse(value1.hasListeners());
        assertFalse(value2.hasListeners());
        cv.getValue();
        assertTrue(value1.hasListeners());
        assertTrue(value2.hasListeners());

        value2.setValue(Integer.valueOf(2));
        values.remove(value2);

        cv.getValue();
        assertEquals(Integer.valueOf(1), cv.getValue());
        assertTrue(value1.hasListeners());
        assertFalse("because value2 is not a part of the calculation the listeners should have been removed", value2.hasListeners());
    }

    @Test
