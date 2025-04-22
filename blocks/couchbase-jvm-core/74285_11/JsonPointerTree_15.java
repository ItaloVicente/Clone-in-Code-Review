
package com.couchbase.client.core.utils.yasjl;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.core.utils.yasjl.Callbacks.JsonPointerCB;

public class JsonPointerTree {

    private final Node root;
    private boolean isRootAPointer;

    public JsonPointerTree() {
        this.root = new Node("", null);
        this.isRootAPointer = false;
    }

    public boolean addJsonPointer(JsonPointer jp) {
        if (isRootAPointer) {
            return false;
        }
        List<String> jpRefTokens = jp.refTokens();
        int jpSize = jpRefTokens.size();

        if (jpSize == 1) {
            isRootAPointer = true;
            return true;
        }
        Node parent = root;
        boolean pathDoesNotExist = false;
        for (int i = 1; i < jpSize; i++) {
            Node childMatch = parent.match(jpRefTokens.get(i));
            if (childMatch == null) {
                parent = parent.addChild(jpRefTokens.get(i), jp.jsonPointerCB());
                pathDoesNotExist = true;
            } else {
                parent = childMatch;
            }
        }
        return pathDoesNotExist;
    }

    public boolean isIntermediaryPath(JsonPointer jp) {
        List<String> jpRefTokens = jp.refTokens();
        int jpSize = jpRefTokens.size();
        if (jpSize == 1) {
            return false;
        }
        Node node = root;
        for (int i = 1; i < jpSize; i++) {
            Node childMatch = node.match(jpRefTokens.get(i));
            if (childMatch == null) {
                return false;
            } else {
                node = childMatch;
            }
        }
        return node.children != null;
    }

    public boolean isTerminalPath(JsonPointer jp) {
        List<String> jpRefTokens = jp.refTokens();
        int jpSize = jpRefTokens.size();

        Node node = root;
        if (jpSize == 1) {
            if (node.children == null) {
                return false;
            }
        }
        for (int i = 1; i < jpSize; i++) {
            Node childMatch = node.match(jpRefTokens.get(i));
            if (childMatch == null) {
                return false;
            } else {
                node = childMatch;
            }
        }
        if (node != null && node.children == null) {
            jp.jsonPointerCB(node.jsonPointerCB);
            return true;
        } else {
            return false;
        }
    }

    class Node {

        private final String value;
        private List<Node> children;
        private final JsonPointerCB jsonPointerCB;

        public Node(String value, JsonPointerCB jsonPointerCB) {
            this.value = value;
            this.children = null;
            this.jsonPointerCB = jsonPointerCB;
        }

        public Node addChild(String value, JsonPointerCB jsonPointerCB) {
            if (children == null) {
                children = new ArrayList<Node>();
            }
            Node child = new Node(value, jsonPointerCB);
            children.add(child);
            return child;
        }

        boolean isIndex(String s) {
            int len = s.length();
            for (int a = 0; a < len; a++) {
                if (a == 0 && s.charAt(a) == '-') continue;
                if (!Character.isDigit(s.charAt(a))) return false;
            }
            return true;
        }

        public Node match(String value) {
            if (this.children == null) {
                return null;
            }
            for (Node child : children) {
                if (child.value.equals(value)) {
                    return child;
                } else if ((child.value.equals("-") && isIndex(value))) {
                    return child;
                }
            }
            return null;
        }
    }
}
