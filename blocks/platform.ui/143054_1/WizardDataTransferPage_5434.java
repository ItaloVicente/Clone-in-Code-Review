		dialog.showClosedProjects(false);
		dialog.open();
		Object[] result = dialog.getResult();
		if (result != null && result.length == 1) {
			return (IPath) result[0];
		}
		return null;
	}

	@Override
