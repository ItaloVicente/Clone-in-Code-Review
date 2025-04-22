			} catch (InvocationTargetException e) {
				StatusManager.getManager().handle(
						MarkerSupportInternalUtilities.errorFor(e));
			} catch (InterruptedException e) {
				StatusManager.getManager().handle(
						MarkerSupportInternalUtilities.errorFor(e));
