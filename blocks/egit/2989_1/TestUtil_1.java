			public boolean test() throws Exception {
				return editor.isActive();
			}

			public void init(SWTBot bot2) {
			}

			public String getFailureMessage() {
				return null;
			}
		}, timeout);
	}
	
