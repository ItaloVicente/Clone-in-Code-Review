    SearchQueryResult search(String index, String query);
    SearchQueryResult search(String index, String query, long timeout, TimeUnit timeUnit);
    SearchQueryResult search(SearchQuery query);
    SearchQueryResult search(SearchQuery query, long timeout, TimeUnit timeUnit);
    SearchQueryResult search(SearchQuery.Builder query);
    SearchQueryResult search(SearchQuery.Builder query, long timeout, TimeUnit timeUnit);

