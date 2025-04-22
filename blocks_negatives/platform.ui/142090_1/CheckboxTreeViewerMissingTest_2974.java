        super.tearDown();
        if (testImage != null) {
            testImage.dispose();
            testImage = null;
        }
    }
