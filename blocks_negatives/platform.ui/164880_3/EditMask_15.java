	private VerifyListener verifyListener = new VerifyListener() {
		@Override
		public void verifyText(VerifyEvent e) {
				e.start == e.end &&
				e.text.length() > 0)
			{
				e.doit=false;
				return;
			}
