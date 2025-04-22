        super.doTearDown();
        try {
            project.delete(true, true, null);
        } catch (CoreException e) {
            fail(e.toString());
        }
        finally{
        	localDirectory = null;
        	project = null;
        	zipFileURL = null;
        	tarFileURL = null;
        }
    }

    private void setup(String propertyName) throws Exception{
