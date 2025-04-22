
			@Override
			protected IAction getAction() {
				if (gpgConfigProblem == null || gpgConfigProblem.isOK()) {
					return null;
				}
				return new GpgConfigProblemReportAction(gpgConfigProblem,
						UIText.RewordHandler_GpgConfigProblem);
			}
