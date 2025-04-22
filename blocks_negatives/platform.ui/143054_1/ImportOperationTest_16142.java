        super.doTearDown();
        try {
            project.delete(true, true, null);
            File topDirectory = new File(localDirectory);
            FileSystemHelper.clear(topDirectory);
        } catch (CoreException e) {
            fail(e.toString());
        }
        finally{
        	project = null;
        	localDirectory = null;
        }
    }

    public void testGetStatus() throws Exception {
        project = FileUtil.createProject("ImportGetStatus");
        File element = new File(localDirectory);
