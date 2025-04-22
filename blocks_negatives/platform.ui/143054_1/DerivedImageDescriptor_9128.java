    /**
     * Creates a new Image on the given device. Note that we defined a new
     * method rather than overloading createImage since this needs to be
     * called by getImageData(), and we want to be absolutely certain not
     * to cause infinite recursion if the base class gets refactored.
     *
     * @param device device to create the image on
     * @return a newly allocated Image. Must be disposed by calling image.dispose().
     */
    private final Image internalCreateImage(Device device) {
        Image originalImage = original.createImage(device);
        Image result = new Image(device, originalImage, flags);
        original.destroyResource(originalImage);
        return result;
    }
