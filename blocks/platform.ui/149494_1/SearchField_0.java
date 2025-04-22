		res.addMenuDetectListener(e -> {
			if (res.getMenu() == null) {
				res.setMenu(parent.getMenu());
				e.doit = true;
			}
		});
