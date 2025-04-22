        final StringBuilder sb = new StringBuilder("NodeInfo{");
        sb.append("viewUri='").append(viewUri).append('\'');
        sb.append(", hostname=").append(hostname);
        sb.append(", configPort=").append(configPort);
        sb.append(", directServices=").append(directServices);
        sb.append(", sslServices=").append(sslServices);
        sb.append('}');
        return sb.toString();
