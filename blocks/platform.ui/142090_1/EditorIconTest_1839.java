		try {
			i1 = fWorkbench.getEditorRegistry().getDefaultEditor(
					"foo.icontest3").getImageDescriptor().createImage();
			i2 = AbstractUIPlugin.imageDescriptorFromPlugin("org.eclipse.ui",
					"icons/full/obj16/file_obj.png").createImage();
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
