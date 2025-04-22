		branchText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				branchTextEdited = true;
			}
		});
		branchText.addVerifyListener(event -> {
			if (event.text.isEmpty()) {
				branchTextEdited = false;
			}
		});
