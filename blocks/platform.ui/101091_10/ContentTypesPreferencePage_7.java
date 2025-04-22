			}
			if (!result.isOK()) {
				StatusUtil.handleStatus(result, StatusManager.SHOW, composite.getShell());
			}
			fileAssociationViewer.refresh(false);
		}));
