				if (!op.isTimedOut() && !op.hasErrored() && !op.isCancelled()) {
					try {
						String json = EntityUtils.toString(response.getEntity());
						op.getCallback().complete(json);
					} catch (ParseException e) {
						op.setException(new OperationException(OperationErrorType.GENERAL, "Bad http headers"));
					} catch (IOException e) {
						op.setException(new OperationException(OperationErrorType.GENERAL, "Error reading response"));
					} catch (IllegalArgumentException e) {
						op.setException(new OperationException(OperationErrorType.GENERAL, "No entity"));
					}
				}
