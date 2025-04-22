                        } else {
                            if (desc.imageDescriptor == null) {
                                desc.imagePath = (String) marker
                                        .getAttribute(MARKER_ATT_KEY);
                                desc.imageDescriptor = getImageDescriptor(desc);
                            }
                            return desc.imageDescriptor;
