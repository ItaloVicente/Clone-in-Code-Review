    public void testNonDependantBundleIcon() {
        Image i1 = null;
        Image i2 = null;
        try {
	        i1 = fWorkbench.getEditorRegistry().getDefaultEditor(
	                "foo.icontest2").getImageDescriptor().createImage();
	        i2 = AbstractUIPlugin.imageDescriptorFromPlugin(
	                "org.eclipse.jdt.ui", "icons/full/obj16/class_obj.png") // layer breaker!
	                .createImage();
	        ImageTests.assertEquals(i1, i2);
        }
        finally {
        	if (i1 != null) {
        		i1.dispose();
        	}
        	if (i2 != null) {
        		i2.dispose();
        	}
        }
    }
