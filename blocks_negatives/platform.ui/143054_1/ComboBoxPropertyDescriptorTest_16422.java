        super.tearDown();
    }

    /**
     * Tests the case where the user does not set an ILabelProvider.
     */
    public void testGetDefaultLabelProvider() {
        ILabelProvider provider = descriptor.getLabelProvider();
        assertEquals("Default label provider is of the wrong type", //$NON-NLS-1$
                ComboBoxLabelProvider.class, provider.getClass());

        for (int i = 0; i < values.length; i++) {
            String expected = values[i];
            assertEquals("Wrong label provided", //$NON-NLS-1$
                    expected, provider.getText(Integer.valueOf(i)));

        }

        testWrongLabel(provider, new Object());
        testWrongLabel(provider, null);
        testWrongLabel(provider, Integer.valueOf(-1));
        testWrongLabel(provider, Integer.valueOf(values.length));
    }

    /**
     * Tests that a bad element object (an Integer outside the accepted range,
     * null or an other Object) returns the empty String.
     * @param provider the provider to test against.
     * @param element the element to test.
     */
    public void testWrongLabel(ILabelProvider provider, Object element) {
        assertEquals("Wrong label provided in bad case", //$NON-NLS-1$
                "", //$NON-NLS-1$
                provider.getText(element));
    }

    /**
     * Tests the case where the user sets their own ILabelProvider.
     */
    public void testSetGetLabelProvider() {
        ILabelProvider provider = new LabelProvider();
        descriptor.setLabelProvider(provider);
        ILabelProvider descProvider = descriptor.getLabelProvider();
        assertSame("Wrong label provider", //$NON-NLS-1$
                provider, descProvider);
    }
