			try {
				proxy.runWhenDone(() -> {
					JGitClientSession.super.sendIdentification(ident,
							extraLines);
					return null;
				});
