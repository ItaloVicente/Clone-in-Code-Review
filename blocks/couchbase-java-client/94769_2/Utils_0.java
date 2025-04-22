            fieldMap.put("s", request.operationId());
            fieldMap.put("i", request.lastLocalId());
            fieldMap.put("b", request.bucket());
            fieldMap.put("l", request.lastLocalSocket());
            fieldMap.put("r", request.lastRemoteSocket());
        }

        try {
            return DefaultObjectMapper.writeValueAsString(fieldMap);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Could not format timeout information for request " + request, e);
            return null;
