
    /**
     * Jackson object mapper for JSON parsing.
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * Accessor to the jackson object mapper.
     */
    @InterfaceStability.Committed
    @InterfaceAudience.Private
    public static ObjectMapper jackson() {
        return OBJECT_MAPPER;
    }

