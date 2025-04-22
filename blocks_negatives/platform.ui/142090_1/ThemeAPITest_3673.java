        assertEquals(2, events.size());
        PropertyChangeEvent event = events.get(0);

        assertEquals(source, event.getSource());
        if (array) {
            assertArrayEquals((Object[]) oldObject, (Object[]) event
                    .getOldValue());
            assertArrayEquals((Object[]) newObject, (Object[]) event
                    .getNewValue());
        } else {
            assertEquals(oldObject, event.getOldValue());
            assertEquals(newObject, event.getNewValue());
        }

        event = events.get(1);
        assertEquals(source, event.getSource());
        if (array) {
            assertArrayEquals((Object[]) oldObject, (Object[]) event
                    .getNewValue());
            assertArrayEquals((Object[]) newObject, (Object[]) event
                    .getOldValue());
        } else {
            assertEquals(oldObject, event.getNewValue());
            assertEquals(newObject, event.getOldValue());
        }
    }

    public void testBooleanDataConversion() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(false, defaultTheme.getBoolean(DATA1));
        assertEquals(false, defaultTheme.getBoolean(DATA2));
        assertEquals(false, defaultTheme.getBoolean(INT1));
        assertEquals(false, defaultTheme.getBoolean(BOGUSKEY));
        assertEquals(true, defaultTheme.getBoolean(BOOL1));
    }

    public void testColorCascadeEvents() {
        ITheme currentTheme = fManager.getCurrentTheme();
        assertNotNull(currentTheme);

        ThemePropertyListener managerListener = new ThemePropertyListener();
        ThemePropertyListener themeListener = new ThemePropertyListener();
        fManager.addPropertyChangeListener(managerListener);
        currentTheme.addPropertyChangeListener(themeListener);

        ColorRegistry colorRegistry = currentTheme.getColorRegistry();
        RGB oldColor = colorRegistry.getRGB(RGBCOLOR);
        RGB newColor = new RGB(121, 9, 121);
        colorRegistry.put(RGBCOLOR, newColor);
        colorRegistry.put(RGBCOLOR, oldColor);

        checkEvents(managerListener, colorRegistry, oldColor, newColor);
        checkEvents(themeListener, colorRegistry, oldColor, newColor);

        fManager.removePropertyChangeListener(managerListener);
        currentTheme.removePropertyChangeListener(themeListener);
    }

    public void testColorFactory() {
        ITheme defaultTheme = getDefaultTheme();
        assertEquals(TestColorFactory.RGB, defaultTheme.getColorRegistry()
                .getRGB(FACTORYCOLOR));
    }

    public void testColorPreferenceListener_def_swtcolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideColorPreference(defaultTheme, store, SWTCOLOR);
    }

    public void testColorPreferenceListener_def_rgbcolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideColorPreference(defaultTheme, store, RGBCOLOR);
    }

    public void testColorPreferenceListener_def_defaultedcolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideColorPreference(defaultTheme, store, DEFAULTEDCOLOR);
    }

    public void testColorPreferenceListener_def_nooverridecolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme defaultTheme = getDefaultTheme();

        testOverrideColorPreference(defaultTheme, store, NOOVERRIDECOLOR);
    }

    public void testColorPreferenceListener_th1_swtcolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideColorPreference(theme1, store, SWTCOLOR);
    }

    public void testColorPreferenceListener_th1_rgbcolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideColorPreference(theme1, store, RGBCOLOR);
    }

    public void testColorPreferenceListener_th1_defaultedcolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideColorPreference(theme1, store, DEFAULTEDCOLOR);
    }

    public void testColorPreferenceListener_th1_nooverridecolor() {
        IPreferenceStore store = PrefUtil.getInternalPreferenceStore();
        ITheme theme1 = getTheme1();

        testOverrideColorPreference(theme1, store, NOOVERRIDECOLOR);
    }

    public void testDataKeySet_data1() {
        ITheme defaultTheme = getDefaultTheme();
