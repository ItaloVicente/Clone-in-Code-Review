                        overlayImage = new OverlayIcon(baseImage,
                                new ImageDescriptor[][] { { natureImage } },
                                new Point(16, 16));
                        imageCache.put(imageKey, overlayImage);
                        return overlayImage;
                    }
                }
            } catch (CoreException e) {
            }
        }
        return IDEInternalWorkbenchImages.getImageDescriptor(baseKey);
    }
