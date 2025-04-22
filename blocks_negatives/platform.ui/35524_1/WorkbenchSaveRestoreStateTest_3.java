						String stateFileName = stateLocation.getPath() + File.separator + "testOnDemandSaveRestoreState.xml";
							
					    OutputStreamWriter writer = null;
				        try {
				            writer = new OutputStreamWriter(new FileOutputStream(stateFileName),UTF-8);
				            
				        } catch (UnsupportedEncodingException e1) {
				        } catch (FileNotFoundException e1) {
