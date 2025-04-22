                    } else if (desc.imageDescriptor != null) {
                        return desc.imageDescriptor;
                    }
                }
            } catch (CoreException e) {
            	Policy.handle(e);
            	return null;
            }
        }
        return null;
    }

    /**
     * Returns the image descriptor with the given relative path.
     */
    ImageDescriptor getImageDescriptor(Descriptor desc) {
