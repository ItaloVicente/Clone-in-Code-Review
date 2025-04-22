						JSONObject elem = ids.getJSONObject(i);
						if (elem.has("id")) {
							String id = elem.getString("id");
							String key = elem.getString("key");
							String value = elem.getString("value");
							rows.add(new RowNoDocs(id, key, value));
						} else if (elem.has("error")) {
							String from = elem.getString("from");
							String reason = elem.getString("reason");
							errors.add(new RowError(from, reason));
						} else {
							throw new ParseException("Unexpected row at line "
									+ i + ": " + json, 0);
						}
