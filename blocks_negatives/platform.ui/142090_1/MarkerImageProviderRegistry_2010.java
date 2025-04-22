                            String path = desc.provider.getImagePath(marker);
                            if (path != desc.imagePath) {
                                desc.imagePath = path;
                                desc.imageDescriptor = getImageDescriptor(desc);
                                return desc.imageDescriptor;
                            }
                            return desc.imageDescriptor;
                        }
