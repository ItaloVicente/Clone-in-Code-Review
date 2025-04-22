            removeBundle();
            support.setDesiredBrowserSupportId(getExtensionId());
            assertFalse(support.hasNonDefaultBrowser());
        }
        finally {
            support.setDesiredBrowserSupportId(null);
        }
    }
