        return Tuple.create(
            TranscoderUtils.encodeStringAsUtf8(
                JacksonTransformers.MAPPER.writeValueAsString(document.content())
            ),
            TranscoderUtils.DOUBLE_COMPAT_FLAGS
        );
