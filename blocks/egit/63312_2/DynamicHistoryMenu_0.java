
		try {
			menuItem.setEnabled(!gfRepo.isDevelop());
		} catch (IOException e) {
			Activator.getDefault().getLog()
					.log(Activator.error(e.getMessage()));
			return;
		}
