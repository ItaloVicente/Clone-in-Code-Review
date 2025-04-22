        ImageData data = getImageData();
        if (data == null) {
            if (!returnMissingImageOnError) {
                return null;
            }
            data = DEFAULT_IMAGE_DATA;
        }

        /*
         * Try to create the supplied image. If there is an SWT Exception try and create
         * the default image if that was requested. Return null if this fails.
         */

        try {
            if (data.transparentPixel >= 0) {
                ImageData maskData = data.getTransparencyMask();
                return new Image(device, data, maskData);
            }
            return new Image(device, data);
        } catch (SWTException exception) {
            if (returnMissingImageOnError) {
                try {
                    return new Image(device, DEFAULT_IMAGE_DATA);
                } catch (SWTException nextException) {
                    return null;
                }
            }
            return null;
        }
    }
