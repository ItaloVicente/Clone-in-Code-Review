		viewMenuMgr.addMenuListener(manager -> {
			repositoriesMenuManager
					.setVisible(!org.eclipse.egit.core.Activator.getDefault()
							.getRepositoryUtil().getRepositories().isEmpty());
			viewMenuMgr.update(true);
		});
