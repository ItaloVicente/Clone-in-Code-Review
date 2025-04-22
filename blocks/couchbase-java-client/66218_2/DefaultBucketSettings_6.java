        StringBuilder s = new StringBuilder("DefaultClusterBucketSettings{")
                .append("name='").append(name).append('\'')
                .append(", type=").append(type)
                .append(", quota=").append(quota)
                .append(", port=").append(port)
                .append(", password='").append(password).append('\'')
                .append(", replicas=").append(replicas)
                .append(", indexReplicas=").append(indexReplicas)
                .append(", enableFlush=").append(enableFlush);
        if (!customSettings.isEmpty()) {
            s.append(", customSettings=").append(customSettings);
        }
        s.append('}');
        return s.toString();
