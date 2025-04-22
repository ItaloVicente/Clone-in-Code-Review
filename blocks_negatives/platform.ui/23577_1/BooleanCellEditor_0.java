	private KeyListener macSelectionListener = new KeyListener(){

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if( e.character == ' ' ) {
				button.setSelection(!button.getSelection());
			}
		}
	};
