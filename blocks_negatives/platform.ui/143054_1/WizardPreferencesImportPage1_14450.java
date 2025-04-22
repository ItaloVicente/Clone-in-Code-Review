        File importFile = new File(getDestinationValue());
        FileInputStream fis = null;
        try {
            if (filters.length > 0) {
                try {
                    fis = new FileInputStream(importFile);
                } catch (FileNotFoundException e) {
                    WorkbenchPlugin.log(e.getMessage(), e);
