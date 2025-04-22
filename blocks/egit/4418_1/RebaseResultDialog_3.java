			conflictList.setLabelProvider(new LabelProvider() {
				@Override
				public String getText(Object element) {
					String path = (String) element;
					if (checkResult != null && !checkResult.isOk()) {
						CheckResultEntry entry = checkResult.getEntry(path);
						if (entry != null) {
							if (!entry.inWorkspace)
								return UIText.RebaseResultDialog_notInWorkspace + SPACE + path;
							if (!entry.shared)
								return UIText.RebaseResultDialog_notShared + SPACE + path;
						}
					}
					return super.getText(element);
				}

			});
