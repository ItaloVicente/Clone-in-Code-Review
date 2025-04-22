			} catch (InvocationTargetException e) {
				status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID, e
						.getMessage(), e);
			} catch (InterruptedException e) {
				status = new Status(IStatus.ERROR, IProgressConstants.PLUGIN_ID, e
						.getMessage(), e);
			} catch (OperationCanceledException e) {
