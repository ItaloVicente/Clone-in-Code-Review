        Assert.assertNotNull(menuMgr.find(ADD_ACTION_ID));
    }

    public void addStandardItems() {
        addElement(new ListElement("red"));
        addElement(new ListElement("blue"));
        addElement(new ListElement("green"));
        addElement(new ListElement("red", true));
    }

    /**
     * Returns <code>true</code> to indicate that a static menu should be used,
     * <code>false</code> to indicate a dynamic menu.
     */
    private boolean useStaticMenu() {
        Object data = getData();
        if (data instanceof String) {
            String arg = (String) data;
        }
        return false;
    }
