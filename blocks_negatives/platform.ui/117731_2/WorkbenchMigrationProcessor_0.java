		} catch (WorkbenchException e) {
			WorkbenchPlugin.log("Failed to load " + legacyWorkbenchFile.getAbsolutePath(), e); //$NON-NLS-1$
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					WorkbenchPlugin.log(e);
				}
			}
