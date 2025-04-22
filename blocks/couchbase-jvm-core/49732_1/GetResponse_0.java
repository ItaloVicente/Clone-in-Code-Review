        return new StringBuilder()
                .append("GetResponse{")
                .append("bucket='").append(bucket()).append('\'')
                .append(", status=").append(status()).append(" (").append(serverStatusCode()).append(')')
                .append(", cas=").append(cas)
                .append(", flags=").append(flags)
                .append(", request=").append(request())
                .append(", content=").append(content().toString(CharsetUtil.UTF_8))
                .append('}').toString();
