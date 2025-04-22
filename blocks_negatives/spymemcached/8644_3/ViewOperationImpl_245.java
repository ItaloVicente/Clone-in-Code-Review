			int errorcode = response.getStatusLine().getStatusCode();
			if (errorcode == HttpURLConnection.HTTP_OK) {
				((ViewCallback) callback).gotData(view);
				callback.receivedStatus(new OperationStatus(true, "OK"));
			} else {
				callback.receivedStatus(new OperationStatus(false, Integer
						.toString(errorcode)));
			}
		} catch (ParseException e) {
			exception = new OperationException(OperationErrorType.GENERAL,
					"Error parsing JSON");
		}
		callback.complete();
	}
