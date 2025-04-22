			@Override
			public void preOpen() {
				super.preOpen();
				contextActivation = contextService.activateContext(QUICK_ACCESS_POPUP_CONTEXT_ID);
			}

