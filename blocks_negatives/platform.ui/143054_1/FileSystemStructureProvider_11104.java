        try {
            return new FileInputStream((File) element);
        } catch (FileNotFoundException e) {
        	IDEWorkbenchPlugin.log(e.getLocalizedMessage(), e);
            return null;
        }
    }
