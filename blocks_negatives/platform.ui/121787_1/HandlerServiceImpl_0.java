		} catch (ExecutionException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} catch (NotDefinedException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} catch (NotEnabledException e) {
			staticContext.set(HANDLER_EXCEPTION, e);
		} catch (NotHandledException e) {
