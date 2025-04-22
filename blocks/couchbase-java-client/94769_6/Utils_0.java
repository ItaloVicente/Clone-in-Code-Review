            fieldMap.put("s", formatServiceType(request));
            putIfNotNull(fieldMap, "i", request.operationId());
            putIfNotNull(fieldMap, "b", request.bucket());
            putIfNotNull(fieldMap, "c", request.lastLocalId());
            putIfNotNull(fieldMap, "l", request.lastLocalSocket());
            putIfNotNull(fieldMap, "r", request.lastRemoteSocket());
        }

        try {
            return DefaultObjectMapper.writeValueAsString(fieldMap);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Could not format timeout information for request " + request, e);
            return null;
