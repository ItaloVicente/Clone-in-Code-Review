    protected String getUserSettingsPayload(UserSettings settings) {
        Map<String, Object> settingsMap = new LinkedHashMap<String, Object>();

        if (settings.name() != null) {
            settingsMap.put("name", settings.name());
        }

        if (settings.password() != null) {
            settingsMap.put("password", settings.password());
        }

        if (settings.roles() != null && settings.roles().size() > 0) {
            StringBuilder sb = new StringBuilder();
            for(UserRole userRole: settings.roles()) {
                if (sb.length() != 0) {
                    sb.append(",");
                }
                sb.append(userRole.role());
                if (userRole.bucket() != null && !userRole.bucket().equals("")) {
                    sb.append("[");
                    sb.append(userRole.bucket());
                    sb.append("]");
                }
            }
            settingsMap.put("roles", sb.toString());
        }

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> setting : settingsMap.entrySet()) {
            sb.append('&').append(setting.getKey()).append('=').append(setting.getValue());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }


