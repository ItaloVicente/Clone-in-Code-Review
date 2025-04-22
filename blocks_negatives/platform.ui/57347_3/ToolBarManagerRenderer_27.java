					runExternalCode(new Runnable() {

						@Override
						public void run() {
							manager.update(false);
							getUpdater().updateContributionItems(ALL_SELECTOR);
						}
