    @Test
    public void shouldQueryClusterApi() {
        ClusterApiClient apiClient = clusterManager.apiClient();

        ClusterApiClient.RestBuilder rest = apiClient.get("settings", "/autoFailover")
                .withHeader(HttpHeaders.Names.ACCEPT, "*/*");
        RestApiRequest request = rest.asRequest();
        RestApiResponse response = rest.execute();

        assertThat(response.request().path()).isEqualTo("/settings/autoFailover");
        assertThat(response.httpStatus().code()).isEqualTo(200);
        assertThat(response.status().isSuccess()).isTrue();
        assertThat(response.body())
                .contains("timeout")
                .contains("enabled")
                .contains("count");
    }

