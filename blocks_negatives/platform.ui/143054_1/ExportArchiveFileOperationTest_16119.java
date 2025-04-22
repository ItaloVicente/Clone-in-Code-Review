        	}
        	root.delete();
        }
        try {
            project.delete(true, true, null);
        } catch (CoreException e) {
            fail(e.toString());
        }
        finally{
        	project = null;
        	localDirectory = null;
        	filePath = null;
        }
