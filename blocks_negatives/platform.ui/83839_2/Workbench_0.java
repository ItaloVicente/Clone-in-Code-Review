			} catch (SWTException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH, e));
			} catch (IOException e) {
				StatusManager.getManager().handle(
						StatusUtil.newStatus(WorkbenchPlugin.PI_WORKBENCH, e));
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (IOException e) {
					}
				}
