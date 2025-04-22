
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CoreEnvironment: {");
        sb.append("sslEnabled=").append(sslEnabled);
        sb.append(", sslKeystoreFile='").append(sslKeystoreFile).append('\'');
        sb.append(", sslKeystorePassword='").append(sslKeystorePassword).append('\'');
        sb.append(", queryEnabled=").append(queryEnabled);
        sb.append(", queryPort=").append(queryPort);
        sb.append(", bootstrapHttpEnabled=").append(bootstrapHttpEnabled);
        sb.append(", bootstrapCarrierEnabled=").append(bootstrapCarrierEnabled);
        sb.append(", bootstrapHttpDirectPort=").append(bootstrapHttpDirectPort);
        sb.append(", bootstrapHttpSslPort=").append(bootstrapHttpSslPort);
        sb.append(", bootstrapCarrierDirectPort=").append(bootstrapCarrierDirectPort);
        sb.append(", bootstrapCarrierSslPort=").append(bootstrapCarrierSslPort);
        sb.append(", ioPoolSize=").append(ioPoolSize);
        sb.append(", computationPoolSize=").append(computationPoolSize);
        sb.append(", responseBufferSize=").append(responseBufferSize);
        sb.append(", requestBufferSize=").append(requestBufferSize);
        sb.append(", binaryServiceEndpoints=").append(binaryServiceEndpoints);
        sb.append(", viewServiceEndpoints=").append(viewServiceEndpoints);
        sb.append(", queryServiceEndpoints=").append(queryServiceEndpoints);
        sb.append(", ioPool=").append(ioPool.getClass().getSimpleName());
        sb.append(", coreScheduler=").append(coreScheduler.getClass().getSimpleName());
        sb.append('}');
        return sb.toString();
    }

