package com.couchbase.client.core.node.locate;

import com.couchbase.client.core.node.Node;
import com.couchbase.client.core.service.ServiceType;

public class AnalyticsLocator extends QueryLocator {

    @Override
    protected boolean checkNode(Node node) {
        return node.serviceEnabled(ServiceType.ANALYTICS);
    }

}
