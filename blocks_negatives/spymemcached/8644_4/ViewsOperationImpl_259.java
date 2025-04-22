	private List<View> parseDesignDocumentForViews(String dn, String ddn,
			String json) throws ParseException {
		List<View> viewList = new LinkedList<View>();
		try {
			JSONObject base = new JSONObject(json);
			if (base.has("error")) {
				return null;
			}
			if (base.has("views")) {
				JSONObject views = base.getJSONObject("views");
				Iterator<?> itr = views.keys();
				while (itr.hasNext()) {
					String viewname = (String) itr.next();
					boolean map = views.getJSONObject(viewname).has("map");
					boolean reduce = views.getJSONObject(viewname)
							.has("reduce");
					viewList.add(new View(dn, ddn, viewname, map, reduce));
				}
			}
		} catch (JSONException e) {
			throw new ParseException("Cannot read json: " + json, 0);
		}
		return viewList;
	}
