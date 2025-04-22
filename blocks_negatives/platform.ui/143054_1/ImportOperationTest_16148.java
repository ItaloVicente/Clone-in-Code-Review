                }
                assertTrue("Import failed to import file " + fileName,
                        k < fileNames.length);
            }
        } catch (CoreException e) {
            fail(e.toString());
        }
    }
