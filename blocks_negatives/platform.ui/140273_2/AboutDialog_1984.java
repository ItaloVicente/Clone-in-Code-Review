        Image aboutImage = null;
        AboutItem item = null;
        if (product != null) {
            ImageDescriptor imageDescriptor = ProductProperties
                    .getAboutImage(product);
            if (imageDescriptor != null) {
