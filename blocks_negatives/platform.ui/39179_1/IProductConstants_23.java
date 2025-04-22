    /**
     * An array of one or more images to be used for this product.  The
     * expectation is that the array will contain the same image rendered
     * at different sizes (16x16 and 32x32).  
     * Products designed to run "headless" typically would not have such images.
     * <p>
     * The value is a comma-separated list of paths, where each path is either 
     * a fully qualified valid URL or a path relative to the product's defining bundle.
     * </p> 
     * <p>
     * If this property is given, then it supercedes <code>WINDOW_IMAGE</code>.
     * </p>
     * <p>
     * It is recommended that products use <code>WINDOW_IMAGES</code> rather than
     * <code>WINDOW_IMAGE</code>, and specify both a 16x16 image and a 32x32 image,
     * to ensure that different sizes of the image are available for different uses
     * in the OS.  For example, on Windows, the 16x16 image is used in the corner of
     * the window and in the task tray, but the 32x32 image is used in the Alt+Tab
     * application switcher.
     * </p>
     */
