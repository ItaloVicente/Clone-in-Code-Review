        String pngDir = "eclipse-png";
        String pngDirProp = System.getProperty(PNG_DIR);
        if (pngDirProp != null) {
            pngDir = pngDirProp;
        }

        String gifDir = "eclipse-gif";
        String gifDirProp = System.getProperty(GIF_DIR);
        if (gifDirProp != null) {
            gifDir = gifDirProp;
        }

        File iconDirectoryRoot = new File(pngDir + "/");
