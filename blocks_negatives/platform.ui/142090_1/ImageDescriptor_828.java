     *
     * <p>
     * Note: this method differs from createResource(Device) in that the returned image
     * must be disposed directly, whereas an image obtained from createResource(...)
     * must be disposed by calling destroyResource(...). It is not possible to
     * mix-and-match. If you obtained the Image from this method, you must not dispose
     * it by calling destroyResource. Clients are encouraged to use
     * create/destroyResource and downcast the result to Image rather than using
     * createImage.
     * </p>
     *
