					});
		} catch (InvocationTargetException e) {
			org.eclipse.egit.ui.Activator.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			org.eclipse.egit.ui.Activator.error(e.getMessage(), e);
		}
