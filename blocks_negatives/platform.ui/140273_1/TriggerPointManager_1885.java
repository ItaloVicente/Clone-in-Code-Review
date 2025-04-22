                    @Override
					public String getStringHint(String key) {
                        if (ITriggerPoint.HINT_INTERACTIVE.equals(key)) {
                            return Boolean.TRUE.toString();
                        }
                        return null;
                    }
