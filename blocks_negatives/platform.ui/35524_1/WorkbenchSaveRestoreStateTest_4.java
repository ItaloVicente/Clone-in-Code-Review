		        try {
					memento = XMLMemento.createReadRoot( new InputStreamReader (
							new FileInputStream(stateFile),UTF-8));
		        } catch (WorkbenchException e) {
		            e.printStackTrace();
		        } catch (FileNotFoundException e) {
		        } catch (UnsupportedEncodingException e) {
		        }
				
