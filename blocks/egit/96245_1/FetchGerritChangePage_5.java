		tagText.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				tagTextEdited = true;
			}
		});
		tagText.addVerifyListener(event -> {
			if (event.text.isEmpty()) {
				tagTextEdited = false;
			}
		});
