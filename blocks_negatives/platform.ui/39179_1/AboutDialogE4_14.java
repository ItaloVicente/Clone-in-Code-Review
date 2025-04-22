        ImageDescriptor desc = info.getFeatureImage();
        Image featureImage = null;

        Button button = new Button(parent, SWT.FLAT | SWT.PUSH);
        button.setData(info);
        featureImage = desc.createImage();
        images.add(featureImage);
        button.setImage(featureImage);
        button.setToolTipText(info.getProviderName());
        
        button.getAccessible().addAccessibleListener(new AccessibleAdapter(){
        	/* (non-Javadoc)
			 * @see org.eclipse.swt.accessibility.AccessibleAdapter#getName(org.eclipse.swt.accessibility.AccessibleEvent)
