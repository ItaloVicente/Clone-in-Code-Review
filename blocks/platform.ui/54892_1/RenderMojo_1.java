        
        String sourceDir = "eclipse-svg";
        String sourceDirProp = System.getProperty(SOURCE_DIR);
        if (sourceDirProp != null) {
            sourceDir = sourceDirProp;
        }
        
        String targetDir = "eclipse-png";
        String targetDirProp = System.getProperty(TARGET_DIR);
        if (targetDirProp != null) {
            targetDir = targetDirProp;
        }
