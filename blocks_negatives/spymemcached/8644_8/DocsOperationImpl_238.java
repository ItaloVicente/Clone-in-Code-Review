	private ViewResponseWithDocs parseDocsViewResult(String json)
			throws ParseException {
		final Collection<RowWithDocs> rows = new LinkedList<RowWithDocs>();
		final Collection<RowError> errors = new LinkedList<RowError>();
		if (json != null) {
			try {
				JSONObject base = new JSONObject(json);
				if (base.has("rows")) {
					JSONArray ids = base.getJSONArray("rows");
					for (int i = 0; i < ids.length(); i++) {
						JSONObject elem = ids.getJSONObject(i);
						String id = elem.getString("id");
						String key = elem.getString("key");
						String value = elem.getString("value");
						rows.add(new RowWithDocs(id, key, value, null));
					}
				}
				if (base.has("errors")) {
					JSONArray ids = base.getJSONArray("errors");
					for (int i = 0; i < ids.length(); i++) {
						JSONObject elem = ids.getJSONObject(i);
						String from = elem.getString("from");
						String reason = elem.getString("reason");
						errors.add(new RowError(from, reason));
					}
				}
			} catch (JSONException e) {
				throw new ParseException("Cannot read json: " + json, 0);
			}
		}
		return new ViewResponseWithDocs(rows, errors);
	}
