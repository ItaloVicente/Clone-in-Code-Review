        if (System.getProperty("overrideConfig") != null) {
            try {
                Scanner scanner = new Scanner( new File("poem.txt") );
                overrideConfig = scanner.useDelimiter("\\A").next();
                scanner.close();
            } catch (Exception ex) {
                throw new RuntimeException("Could not load override file: ", ex);
            }
        } else {
            overrideConfig = null;
        }

