			@Override
			public int getNumberOfControls() {
				return super.getNumberOfControls() + 1;
			}

			@Override
			protected void doFillIntoGrid(Composite parent, int numColumns) {
				super.doFillIntoGrid(parent, numColumns - 1);
			}

			@Override
			protected void adjustForNumColumns(int numColumns) {
				super.adjustForNumColumns(numColumns - 1);
			}

			@Override
			protected boolean doCheckState() {
				String value = getTextControl().getText();
				value = value.trim();
				if (value.length() == 0 && isEmptyStringAllowed()) {
					return true;
				}

				IStringVariableManager manager = VariablesPlugin.getDefault().getStringVariableManager();
				String dirName;
				try {
					dirName = manager.performStringSubstitution(value);
				} catch (CoreException e) {
					return false;
				}

				File file = new File(dirName);
				return file.isDirectory();
			}

