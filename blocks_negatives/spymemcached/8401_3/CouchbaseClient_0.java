			public void complete(String response) {

				Collection<View> views;
				try {
					views = DocParserUtils.parseDesignDocumentForViews(bucketName, designDocumentName, response);
					crv.set(null);
					for (View v : views) {
						if (v.getViewName().equals(viewName)) {
							crv.set(v);
							break;
						}
					}
				} catch (ParseException e) {
					crv.set(null);
					e.printStackTrace();
				}
