		} catch (NotDefinedException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotEnabledException e) {
			Activator.handleError(e.getMessage(), e, true);
		} catch (NotHandledException e) {
			Activator.handleError(e.getMessage(), e, true);
