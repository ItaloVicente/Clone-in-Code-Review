	  try{
		  View view = client.getView(DESIGN_DOC, VIEW_NAME_MAPRED);
		  Query query = new Query();
		  query.setReduce(false).setStale(Stale.FALSE);
		  int docsPerPage = 0;
		  client.paginatedQuery(view, query, docsPerPage);
	  }
	  catch (IllegalArgumentException e) {
		  assertEquals(e.getMessage(), "Http Error: 400 Reason: Bad Request "
				  + "Details: {\"docsPerPage\":\"Documents per page cannot be less than 1}");
	  }
