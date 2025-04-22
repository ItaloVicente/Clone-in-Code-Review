			@Override
			protected boolean doCheckState() {
				String fileName = getTextControl().getText();
				fileName = fileName.trim();
				if (fileName.length() == 0 && isEmptyStringAllowed()) {
					return true;
				}
				File file = new File(fileName);
				return !file.exists() || file.isDirectory();
			}
