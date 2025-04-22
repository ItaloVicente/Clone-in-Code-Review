    @Test
    public void shouldHaveRawButNoCustomSettingsWhenGet() {
        List<BucketSettings> buckets = clusterManager.getBuckets();
        assertThat(buckets).isNotEmpty();
        for (BucketSettings bucket : buckets) {
            assertThat(bucket.customSettings()).isEmpty();
            assertThat(bucket.raw().isEmpty()).isFalse();
        }
    }

