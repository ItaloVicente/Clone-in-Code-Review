        	Image image = (Image) images.get(imageDesc);
        	if (image == null) {
        		try {
        			image = resourceManager.createImage(imageDesc);
        			images.put(imageDesc, image);
        		}
        		catch (DeviceResourceException e) {
        			WorkbenchPlugin.log(getClass(), "getImage", e); //$NON-NLS-1$
        		}
        	}
        	return image;
        }
        return null;
    }
