@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "nodeLocator"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = CouchbaseBucketConfig.class, name = "vbucket"),
    @JsonSubTypes.Type(value = MemcacheBucketConfig.class, name = "ketama")
})
