        for (int i = 0; i < images.size(); ++i) {
            Image image = images.get(i);
            image.dispose();
        }

        return super.close();
    }

    /*
     * (non-Javadoc) Method declared on Window.
     */
    @Override
