import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.silence.framework.element.loader.ElementLoader;
import org.silence.framework.element.loader.ElementLoaderImpl;
import org.silence.framework.holder.XmlDocumentHolder;

import java.util.Iterator;

import static org.junit.Assert.assertNotNull;

public class ElementLoaderImplTest {
    XmlDocumentHolder xmlHolder;
    ElementLoader elementLoader;

    @Before
    public void setUp() throws Exception {
        xmlHolder = new XmlDocumentHolder();
        elementLoader = new ElementLoaderImpl();

    }

    @After
    public void tearDown() throws Exception {
        xmlHolder = null;
        elementLoader = null;
    }

    @Test
    public void testAddElements() {
        String filePath = "src/main/resources/ElementLoaderImpl.xml";
        Document document = xmlHolder.getDocument(filePath);
        assertNotNull(document);
        elementLoader.addBeanElements(document);
        Element e = elementLoader.getBeanElement("test1");
        assertNotNull(e);
        for (Iterator iter = elementLoader.getBeanElements().iterator(); iter.hasNext(); ) {
            System.out.println(iter.next());
        }
    }

}
