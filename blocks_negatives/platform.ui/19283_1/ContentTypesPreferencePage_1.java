						} catch (CoreException ex) {
							StatusUtil.handleStatus(ex.getStatus(),
									StatusManager.SHOW, shell);
							WorkbenchPlugin.log(ex);
						} finally {
							fileAssociationViewer.refresh(false);
