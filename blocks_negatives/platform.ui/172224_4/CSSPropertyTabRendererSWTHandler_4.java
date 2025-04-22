							CTabFolderRenderer renderer = ((CTabFolder) control).getRenderer();
							if (renderer != null && renderer.getClass() == targetClass) {
								return;
							}
							Constructor<?> constructor = targetClass
									.getConstructor(CTabFolder.class);
							if (constructor != null) {
								Object rend = constructor.newInstance(control);
								if (rend != null && rend instanceof CTabFolderRenderer) {
									((CTabFolder) control).setRenderer((CTabFolderRenderer)rend);
								}
