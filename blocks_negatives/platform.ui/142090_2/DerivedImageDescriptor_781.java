        try {
            return internalCreateImage(device);
        } catch (SWTException e) {
            throw new DeviceResourceException(this, e);
        }
    }
