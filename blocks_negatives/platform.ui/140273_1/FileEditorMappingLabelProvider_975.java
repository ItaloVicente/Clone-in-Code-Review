        if (element instanceof IFileEditorMapping) {
            Image image = ((IFileEditorMapping) element).getImageDescriptor()
                    .createImage();
            imagesToDispose.add(image);
            return image;
        }
        return null;
    }
