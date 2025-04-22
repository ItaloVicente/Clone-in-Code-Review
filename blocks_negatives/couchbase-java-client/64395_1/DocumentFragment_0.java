        for (SubdocOperationResult<OPERATION> result : resultList) {
            if (path.equals(result.path()) && !(result.value() instanceof Exception)) {
                return true;
            }
        }
        return false;
