				});
			} catch (InvocationTargetException e) {
				Activator.handleError(UIText.SharingWizard_failed,
						e.getCause(), true);
				return false;
			} catch (InterruptedException e) {
