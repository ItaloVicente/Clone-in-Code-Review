        CCombo list = (CCombo) fViewer.getControl();
        return list.getItem(at);
    }

    public static void main(String args[]) {
        junit.textui.TestRunner.run(CComboViewerTest.class);
    }

    /**
     * TODO: Determine if this test is applicable to ComboViewer
     */
    @Override
