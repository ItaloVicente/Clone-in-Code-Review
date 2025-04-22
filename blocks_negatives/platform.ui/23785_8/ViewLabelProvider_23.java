        Image image = images.get(desc);
        if (image == null) {
            image = desc.createImage();
            images.put(desc, image);
        }
        return image;
    }
