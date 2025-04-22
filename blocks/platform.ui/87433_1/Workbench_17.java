			try {
				handlerService.executeCommand(commandId, event);
				event.doit = false;
			} catch (NotDefinedException e1) {
			} catch (NotEnabledException e2) {
			} catch (NotHandledException e3) {
			} catch (ExecutionException ex) {
				StatusUtil.handleStatus(ex, StatusManager.SHOW | StatusManager.LOG);
