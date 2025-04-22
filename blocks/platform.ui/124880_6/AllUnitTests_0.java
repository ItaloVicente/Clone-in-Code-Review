package org.eclipse.urischeme.internal.registration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public class PlistFileWriter {

	private static final String XPATH_PLIST_DICT_CF_BUNDLE_URL_TYPES_KEY = "/plist/dict/key[text()=\"CFBundleURLTypes\"]"; //$NON-NLS-1$
	private static final String XPATH_PLIST_DICT_CF_BUNDLE_URL_TYPES_ARRAY = XPATH_PLIST_DICT_CF_BUNDLE_URL_TYPES_KEY
			+ "/following-sibling::array"; //$NON-NLS-1$
	private static final String ELEMENT_NAME_KEY = "key"; //$NON-NLS-1$
	private static final String ELEMENT_NAME_ARRAY = "array"; //$NON-NLS-1$
	private static final String ELEMENT_NAME_STRING = "string"; //$NON-NLS-1$
	private static final String ELEMENT_NAME_DICT = "dict"; //$NON-NLS-1$
	private static final String KEY_VALUE_CF_BUNDLE_URL_TYPES = "CFBundleURLTypes"; //$NON-NLS-1$
	private static final String KEY_VALUE_CF_BUNDLE_URL_NAME = "CFBundleURLName"; //$NON-NLS-1$
	private static final String KEY_VALUE_CF_BUNDLE_URL_SCHEMES = "CFBundleURLSchemes"; //$NON-NLS-1$
	private Document document;
	private Element array;

	public PlistFileWriter(InputStream inputStream) {
		this.document = getDom(inputStream);
		this.array = getOrCreateBundleUrlTypesAndArray();
	}

	public void addScheme(String scheme, String schemeDescription) {
		throwExceptionOnIllegalScheme(scheme);

		if (getExistingElementFor(scheme) != null) {
			return;
		}

		addIndent(array, 3);
		Element dictInArray = addChildNode(array, ELEMENT_NAME_DICT, null);

		addIndent(dictInArray, 4);
		addChildNode(dictInArray, ELEMENT_NAME_KEY, KEY_VALUE_CF_BUNDLE_URL_NAME);

		addIndent(dictInArray, 5);
		addChildNode(dictInArray, ELEMENT_NAME_STRING, schemeDescription);

		addIndent(dictInArray, 4);
		addChildNode(dictInArray, ELEMENT_NAME_KEY, KEY_VALUE_CF_BUNDLE_URL_SCHEMES);

		addIndent(dictInArray, 5);
		Element schemeArray = addChildNode(dictInArray, ELEMENT_NAME_ARRAY, null);

		addIndent(schemeArray, 6);
		addChildNode(schemeArray, ELEMENT_NAME_STRING, scheme);

		addIndent(schemeArray, 5);
		addIndent(dictInArray, 3);
	}

	public void removeScheme(String scheme) {
		throwExceptionOnIllegalScheme(scheme);

		Element dict = getExistingElementFor(scheme);
		if (dict == null) {
			return;
		}

		Node arrayNode = dict.getParentNode();
		removeTextNode(arrayNode, dict.getPreviousSibling()); // remove tab and line break before dict
		arrayNode.removeChild(dict);
	}

	public OutputStream getResult() {
		if (document == null || array == null) {
			throw new IllegalStateException("Method can only be called once."); //$NON-NLS-1$
		}

		boolean hasDict = false;
		for (int i = 0; i < array.getChildNodes().getLength(); i++) {
			Node child = array.getChildNodes().item(i);
			if ("dict".equals(child.getNodeName())) { //$NON-NLS-1$
				hasDict = true;
				break;
			}
		}
		if (!hasDict) {
			Node keyNode = evaluateXpathOnElement(document, XPATH_PLIST_DICT_CF_BUNDLE_URL_TYPES_KEY);
			if (keyNode != null) {
				keyNode.getParentNode().removeChild(keyNode);
				array.getParentNode().removeChild(array);
			}
		} else {
			addIndent(array, 2);
		}

		OutputStream out = transformDocument();
		resetState();
		return out;
	}

	private OutputStream transformDocument() {
		try {
			DOMSource source = new DOMSource(this.document);
			OutputStream out = new ByteArrayOutputStream();
			TransformerFactory.newInstance().newTransformer().transform(source, new StreamResult(out));
			return out;
		} catch (TransformerException e) {
			throw new IllegalStateException(e);
		}
	}

	private Document getDom(InputStream inputStream) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);
			return doc;
		} catch (ParserConfigurationException | IOException | SAXException e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private Element getOrCreateBundleUrlTypesAndArray() {
		Element arrayNode = evaluateXpathOnElement(this.document, XPATH_PLIST_DICT_CF_BUNDLE_URL_TYPES_ARRAY);
		if (arrayNode != null) {
			if (removeTextNode(arrayNode, arrayNode.getLastChild())) {
				addLineBreak(arrayNode);
			}
		} else {
			Element plistElement = document.getDocumentElement();
			NodeList dictElements = plistElement.getElementsByTagName(ELEMENT_NAME_DICT);
			if (dictElements.getLength() == 0) {
				throw new IllegalStateException("Top level 'DICT' element could not be found"); //$NON-NLS-1$
			}
			Node dictElement = dictElements.item(0);
			addIndent(dictElement, 1);
			addChildNode(dictElement, ELEMENT_NAME_KEY, KEY_VALUE_CF_BUNDLE_URL_TYPES);

			addIndent(dictElement, 2);
			arrayNode = addChildNode(dictElement, ELEMENT_NAME_ARRAY, null);
		}
		return arrayNode;
	}

	private boolean removeTextNode(Node parent, Node textNode) {
		if (textNode instanceof Text) {
			parent.removeChild(textNode);
			return true;
		}
		return false;
	}

	private Element addChildNode(Node parent, String name, String value) {
		Element newElement = document.createElement(name);
		if (value != null) {
			newElement.appendChild(document.createTextNode(value));
		} else {
			addLineBreak(newElement);
		}
		parent.appendChild(newElement);
		addLineBreak(parent);
		return newElement;
	}

	private void addLineBreak(Node node) {
		node.appendChild(document.createTextNode("\n")); //$NON-NLS-1$
	}

	private void addIndent(Node node, int indent) {
		String text = ""; //$NON-NLS-1$
		for (int i = 0; i < indent; i++) {
			text += "	"; //$NON-NLS-1$
		}
		node.appendChild(document.createTextNode(text));
	}

	private void resetState() {
		this.array = null;
		this.document = null;
	}

	private Element getExistingElementFor(String scheme) {
		String xpathToSchemeDictElement = XPATH_PLIST_DICT_CF_BUNDLE_URL_TYPES_ARRAY
				+ "/dict/key[text()=\"CFBundleURLSchemes\"]/following-sibling::array/string[text()=\"" + scheme //$NON-NLS-1$
				+ "\"]/../.."; //$NON-NLS-1$

		return evaluateXpathOnElement(this.document, xpathToSchemeDictElement);
	}

	private Element evaluateXpathOnElement(Node node, String xpath) {
		try {
			XPathExpression xpathExpression = XPathFactory.newInstance().newXPath().compile(xpath);
			NodeList nodeList = (NodeList) xpathExpression.evaluate(node, XPathConstants.NODESET);
			return nodeList.getLength() == 0 ? null : (Element) nodeList.item(0);
		} catch (XPathExpressionException e) {
			throw new IllegalStateException(e); // cannot happen
		}
	}

	private void throwExceptionOnIllegalScheme(String scheme) {
		try {
			new URI(scheme, "foo", ""); //$NON-NLS-1$ //$NON-NLS-2$
		} catch (URISyntaxException e1) {
			throw new IllegalArgumentException("'Scheme' must only contain letters"); //$NON-NLS-1$
		}
	}

}
