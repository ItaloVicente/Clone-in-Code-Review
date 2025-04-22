        if (span != null) {
            Map<String, Object> exData = new HashMap<String, Object>();
            exData.put(Fields.ERROR_KIND, "Exception");
            exData.put(Fields.ERROR_OBJECT, throwable);
            exData.put(Fields.EVENT, "failed");
            exData.put(Fields.MESSAGE, throwable.getMessage());
            span.log(exData);
        }
