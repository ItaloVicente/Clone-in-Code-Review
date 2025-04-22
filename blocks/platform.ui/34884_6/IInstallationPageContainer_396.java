package org.eclipse.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public final class XMLMemento implements IMemento {
    private Document factory;

    private Element element;

    public static XMLMemento createReadRoot(Reader reader)
            throws WorkbenchException {
        return createReadRoot(reader, null);
    }

    public static XMLMemento createReadRoot(Reader reader, String baseDir)
            throws WorkbenchException {
        String errorMessage = null;
        Exception exception = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder parser = factory.newDocumentBuilder();
            InputSource source = new InputSource(reader);
            if (baseDir != null) {
				source.setSystemId(baseDir);
			}

			parser.setErrorHandler(new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception) throws SAXException {
				}

				@Override
				public void error(SAXParseException exception) throws SAXException {
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
				}
			});

            Document document = parser.parse(source);
            NodeList list = document.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node instanceof Element) {
					return new XMLMemento(document, (Element) node);
				}
            }
        } catch (ParserConfigurationException e) {
            exception = e;
            errorMessage = WorkbenchMessages.XMLMemento_parserConfigError;
        } catch (IOException e) {
            exception = e;
            errorMessage = WorkbenchMessages.XMLMemento_ioError; 
        } catch (SAXException e) {
            exception = e;
            errorMessage = WorkbenchMessages.XMLMemento_formatError; 
        }

        String problemText = null;
        if (exception != null) {
			problemText = exception.getMessage();
		}
        if (problemText == null || problemText.length() == 0) {
			problemText = errorMessage != null ? errorMessage
                    : WorkbenchMessages.XMLMemento_noElement;
		} 
        throw new WorkbenchException(problemText, exception);
    }

	public static XMLMemento createWriteRoot(String type) throws DOMException {
        Document document;
        try {
            document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();
            Element element = document.createElement(type);
            document.appendChild(element);
            return new XMLMemento(document, element);
        } catch (ParserConfigurationException e) {
            throw new Error(e.getMessage());
        }
    }

    public XMLMemento(Document document, Element element) {
        super();
        this.factory = document;
        this.element = element;
    }

	@Override
	public IMemento createChild(String type) throws DOMException {
        Element child = factory.createElement(type);
        element.appendChild(child);
        return new XMLMemento(factory, child);
    }

	@Override
	public IMemento createChild(String type, String id) throws DOMException {
        Element child = factory.createElement(type);
        child.setAttribute(TAG_ID, id == null ? "" : id); //$NON-NLS-1$
        element.appendChild(child);
        return new XMLMemento(factory, child);
    }

	public IMemento copyChild(IMemento child) throws DOMException {
        Element childElement = ((XMLMemento) child).element;
        Element newElement = (Element) factory.importNode(childElement, true);
        element.appendChild(newElement);
        return new XMLMemento(factory, newElement);
    }

    @Override
	public IMemento getChild(String type) {

        NodeList nodes = element.getChildNodes();
        int size = nodes.getLength();
        if (size == 0) {
			return null;
		}

        for (int nX = 0; nX < size; nX++) {
            Node node = nodes.item(nX);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (element.getNodeName().equals(type)) {
					return new XMLMemento(factory, element);
				}
            }
        }

        return null;
    }

	@Override
	public IMemento[] getChildren() {

		final NodeList nodes = element.getChildNodes();
		int size = nodes.getLength();
		if (size == 0) {
			return new IMemento[0];
		}

		ArrayList list = new ArrayList(size);
		for (int nX = 0; nX < size; nX++) {
			final Node node = nodes.item(nX);
			if (node instanceof Element)
				list.add(node);
		}

		size = list.size();
		IMemento[] results = new IMemento[size];
		for (int x = 0; x < size; x++) {
			results[x] = new XMLMemento(factory, (Element) list.get(x));
		}
		return results;
	}

    @Override
	public IMemento[] getChildren(String type) {

        NodeList nodes = element.getChildNodes();
        int size = nodes.getLength();
        if (size == 0) {
			return new IMemento[0];
		}

        ArrayList list = new ArrayList(size);
        for (int nX = 0; nX < size; nX++) {
            Node node = nodes.item(nX);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (element.getNodeName().equals(type)) {
					list.add(element);
				}
            }
        }

        size = list.size();
        IMemento[] results = new IMemento[size];
        for (int x = 0; x < size; x++) {
            results[x] = new XMLMemento(factory, (Element) list.get(x));
        }
        return results;
    }

    @Override
	public Float getFloat(String key) {
        Attr attr = element.getAttributeNode(key);
        if (attr == null) {
			return null;
		}
        String strValue = attr.getValue();
        try {
            return new Float(strValue);
        } catch (NumberFormatException e) {
            WorkbenchPlugin.log("Memento problem - Invalid float for key: " //$NON-NLS-1$
                    + key + " value: " + strValue, e); //$NON-NLS-1$
            return null;
        }
    }

	@Override
	public String getType() {
		return element.getNodeName();
	}

    @Override
	public String getID() {
        return element.getAttribute(TAG_ID);
    }

    @Override
	public Integer getInteger(String key) {
        Attr attr = element.getAttributeNode(key);
        if (attr == null) {
			return null;
		}
        String strValue = attr.getValue();
        try {
            return new Integer(strValue);
        } catch (NumberFormatException e) {
            WorkbenchPlugin
                    .log("Memento problem - invalid integer for key: " + key //$NON-NLS-1$
                            + " value: " + strValue, e); //$NON-NLS-1$
            return null;
        }
    }

    @Override
	public String getString(String key) {
        Attr attr = element.getAttributeNode(key);
        if (attr == null) {
			return null;
		}
        return attr.getValue();
    }

	@Override
	public Boolean getBoolean(String key) {
        Attr attr = element.getAttributeNode(key);
        if (attr == null) {
			return null;
		}
        return Boolean.valueOf(attr.getValue());
	}

	@Override
	public String getTextData() throws DOMException {
        Text textNode = getTextNode();
        if (textNode != null) {
            return textNode.getData();
        }
        return null;
    }

	@Override
	public String[] getAttributeKeys() {
		NamedNodeMap map = element.getAttributes();
		int size = map.getLength();
		String[] attributes = new String[size];
		for (int i = 0; i < size; i++) {
			Node node = map.item(i);
			attributes[i] = node.getNodeName();
		}
		return attributes;
	}

    private Text getTextNode() {
        NodeList nodes = element.getChildNodes();
        int size = nodes.getLength();
        if (size == 0) {
			return null;
		}
        for (int nX = 0; nX < size; nX++) {
            Node node = nodes.item(nX);
            if (node instanceof Text) {
                return (Text) node;
            }
        }
        return null;
    }

	private void putElement(Element element, boolean copyText) throws DOMException {
        NamedNodeMap nodeMap = element.getAttributes();
        int size = nodeMap.getLength();
        for (int i = 0; i < size; i++) {
            Attr attr = (Attr) nodeMap.item(i);
            putString(attr.getName(), attr.getValue());
        }

        NodeList nodes = element.getChildNodes();
        size = nodes.getLength();
        boolean needToCopyText = copyText;
        for (int i = 0; i < size; i++) {
            Node node = nodes.item(i);
            if (node instanceof Element) {
                XMLMemento child = (XMLMemento) createChild(node.getNodeName());
                child.putElement((Element) node, true);
            } else if (node instanceof Text && needToCopyText) {
                putTextData(((Text) node).getData());
                needToCopyText = false;
            }
        }
    }

	@Override
	public void putFloat(String key, float f) throws DOMException {
        element.setAttribute(key, String.valueOf(f));
    }

	@Override
	public void putInteger(String key, int n) throws DOMException {
        element.setAttribute(key, String.valueOf(n));
    }

	@Override
	public void putMemento(IMemento memento) throws DOMException {
        putElement(((XMLMemento) memento).element, false);
    }

	@Override
	public void putString(String key, String value) throws DOMException {
        if (value == null) {
			return;
		}
        element.setAttribute(key, value);
    }

	@Override
	public void putBoolean(String key, boolean value) throws DOMException {
		element.setAttribute(key, value ? "true" : "false"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void putTextData(String data) throws DOMException {
        Text textNode = getTextNode();
        if (textNode == null) {
            textNode = factory.createTextNode(data);
			element.insertBefore(textNode, element.getFirstChild());
        } else {
            textNode.setData(data);
        }
    }

    public void save(Writer writer) throws IOException {
    	DOMWriter out = new DOMWriter(writer);
        try {
        	out.print(element);
    	} finally {
    		out.close();
    	}
	}

	@Override
	public String toString() {
		try {
			StringWriter writer = new StringWriter();
			save(writer);
			return writer.toString();
		} catch (IOException e) {
			return super.toString();
		}
	}

    private static final class DOMWriter extends PrintWriter {
    	
    	private static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"; //$NON-NLS-1$

    	public DOMWriter(Writer output) {
    		super(output);
    		println(XML_VERSION);
    	}

        public void print(Element element) {
        	boolean hasChildren = element.hasChildNodes();
        	startTag(element, hasChildren);
        	if (hasChildren) {
	        	boolean prevWasText = false;
	        	NodeList children = element.getChildNodes();
	    		for (int i = 0; i < children.getLength(); i++) {
	    			Node node = children.item(i);
	    			if (node instanceof Element) {
	    				if (!prevWasText) {
	    					println();
	    				}
	    				print((Element) children.item(i));
	    				prevWasText = false;
	    			}
	    			else if (node instanceof Text) {
	    				print(getEscaped(node.getNodeValue()));
	    				prevWasText = true;
	    			}
	    		}
	    		if (!prevWasText) {
	    			println();
	    		}
	    		endTag(element);
        	}
    	}

    	private void startTag(Element element, boolean hasChildren) {
    		StringBuffer sb = new StringBuffer();
    		sb.append("<"); //$NON-NLS-1$
    		sb.append(element.getTagName());
    		NamedNodeMap attributes = element.getAttributes();
   			for (int i = 0;  i < attributes.getLength(); i++) {
   				Attr attribute = (Attr)attributes.item(i);
				sb.append(" "); //$NON-NLS-1$
				sb.append(attribute.getName());
				sb.append("=\""); //$NON-NLS-1$
				sb.append(getEscaped(String.valueOf(attribute.getValue())));
				sb.append("\""); //$NON-NLS-1$
   			}
   			sb.append(hasChildren ? ">" : "/>"); //$NON-NLS-1$ //$NON-NLS-2$
   			print(sb.toString());
    	}

    	private void endTag(Element element) {
    		StringBuffer sb = new StringBuffer();
    		sb.append("</"); //$NON-NLS-1$
    		sb.append(element.getNodeName());
    		sb.append(">"); //$NON-NLS-1$
   			print(sb.toString());
    	}
    	
    	private static void appendEscapedChar(StringBuffer buffer, char c) {
    		String replacement = getReplacement(c);
    		if (replacement != null) {
    			buffer.append('&');
    			buffer.append(replacement);
    			buffer.append(';');
    		} else if (c==9 || c==10 || c==13 || c>=32){
    			buffer.append(c);
    		}
    	}

    	private static String getEscaped(String s) {
    		StringBuffer result = new StringBuffer(s.length() + 10);
    		for (int i = 0; i < s.length(); ++i) {
				appendEscapedChar(result, s.charAt(i));
			}
    		return result.toString();
    	}

    	private static String getReplacement(char c) {
    		switch (c) {
    			case '<' :
    				return "lt"; //$NON-NLS-1$
    			case '>' :
    				return "gt"; //$NON-NLS-1$
    			case '"' :
    				return "quot"; //$NON-NLS-1$
    			case '\'' :
    				return "apos"; //$NON-NLS-1$
    			case '&' :
    				return "amp"; //$NON-NLS-1$
				case '\r':
					return "#x0D"; //$NON-NLS-1$
				case '\n':
					return "#x0A"; //$NON-NLS-1$
				case '\u0009':
					return "#x09"; //$NON-NLS-1$
    		}
    		return null;
    	}
    }
}
