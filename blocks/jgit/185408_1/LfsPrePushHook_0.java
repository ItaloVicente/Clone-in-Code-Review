
				Protocol.Action verifyAction = o.actions.get(ACTION_VERIFY);
				if (verifyAction != null) {
					HttpConnection verifyServer = LfsConnectionFactory
						.getLfsContentConnection(getRepository()
							METHOD_POST);

					verifyServer.setDoOutput(true);
					verifyServer.getOutputStream().write(Protocol.gson().toJson(ptr).getBytes(UTF_8));
					int responseCode = verifyServer.getResponseCode();
					if (responseCode != HTTP_OK) {
						throw new IOException(
							MessageFormat.format(LfsText.get().serverFailure
					}
				}
