				try {
					handlerService.executeCommand(commandId, event);
					event.doit = false;
				} catch (NotDefinedException e) {
				} catch (NotEnabledException e) {
				} catch (NotHandledException e) {
				} catch (ExecutionException ex) {
					StatusUtil.handleStatus(ex, StatusManager.SHOW | StatusManager.LOG);
				}
