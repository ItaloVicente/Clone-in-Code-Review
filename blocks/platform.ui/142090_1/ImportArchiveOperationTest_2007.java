				}
				assertTrue("Import failed to import file " + fileName,
						k < fileNames.length);
			}
		} catch (CoreException e) {
			fail(e.toString());
		}
	}

	private boolean closeZipFile(ZipFile zipFile){
		try{
			zipFile.close();
		}
		catch(IOException e){
			fail("Could not close zip file " + zipFile.getName(), e);
			return false;
		}
		return true;
	}
