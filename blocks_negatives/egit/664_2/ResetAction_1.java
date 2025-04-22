				});
			} catch (InvocationTargetException e) {
				Activator.handleError(UIText.ResetAction_resetFailed, e, true);
			} catch (InterruptedException e) {
				Activator.handleError(UIText.ResetAction_resetFailed, e, true);
			}
