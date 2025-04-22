			public void complete(String response) {
				try {
					crv.set(DocParserUtils.parseDesignDocumentForViews(bucketName, designDocumentName, response));
				} catch (ParseException e) {
					getLogger().error(e.getMessage());
				}
