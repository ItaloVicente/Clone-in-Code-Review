			bot.waitWhile(new ICondition() {

				@Override
				public boolean test() throws Exception {
					return !bot.button("Next >").isEnabled();
				}

				@Override
				public void init(SWTBot bot) {
				}
				@Override
				public String getFailureMessage() {
					return null;
				}
			}, 180000); // Time to clone repo, up to 3 minutes
