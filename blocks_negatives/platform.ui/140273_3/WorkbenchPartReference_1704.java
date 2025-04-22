					((IWorkbenchPart3) legacyPart)
							.removePartPropertyListener(partPropertyChangeListener);
                }
            } catch (Exception e) {
                WorkbenchPlugin.log(e);
            }
