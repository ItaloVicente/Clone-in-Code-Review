        Image result = createImage(false, device);
        if (result == null) {
            throw new DeviceResourceException(this);
        }
        return result;
    }
