			} catch (Throwable e) {// Handle the exception the same way as the workbench
				handler.handleException(e);
				break;
			}

			if (System.currentTimeMillis() - t > T_MAX) {
				break;
			}
		}
	}

	@Override
