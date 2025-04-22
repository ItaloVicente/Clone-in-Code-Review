        }
        if (defaultImage != null) {
            JFaceResources.getResources().destroyImage(defaultImageDescriptor);
            defaultImage = null;
        }
    }

    /*
     * (non-Javadoc) Method declared on IWizard.
     */
    @Override
