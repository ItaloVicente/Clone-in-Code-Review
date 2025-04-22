					if (event
							.getJob()
							.getProperty(
									IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY) == Boolean.TRUE) {
						statusAdapter
								.setProperty(
										IProgressConstants.NO_IMMEDIATE_ERROR_PROMPT_PROPERTY,
										Boolean.TRUE);
						StatusAdapterHelper.getInstance().putStatusAdapter(
								info, statusAdapter);
