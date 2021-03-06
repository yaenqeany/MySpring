import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.silence.framework.element.LeafElement;
import org.silence.framework.element.PropertyElement;
import org.silence.framework.element.RefElement;
import org.silence.framework.element.ValueElement;
import org.silence.framework.element.auto.Autowire;
import org.silence.framework.element.loader.ElementLoader;
import org.silence.framework.element.loader.ElementLoaderImpl;
import org.silence.framework.element.parser.BeanElementParser;
import org.silence.framework.element.parser.BeanElementParserImpl;
import org.silence.framework.holder.XmlDocumentHolder;


public class ElementParserImplTest {
    private XmlDocumentHolder xmlHolder;

    private ElementLoader elementLoader;

    private BeanElementParser parser;
    @Before
    public void setUp() throws Exception {
        xmlHolder = new XmlDocumentHolder();
        elementLoader = new ElementLoaderImpl();
        String filePath = "src/main/resources/elementParserImpl.xml";
        Document doc = xmlHolder.getDocument(filePath);
        elementLoader.addBeanElements(doc);
        parser = new BeanElementParserImpl();
    }

    @After
    public void tearDown() throws Exception {
        xmlHolder = null;
        elementLoader = null;
    }

    @Test
    public void testIsLazy() {

        //第一个元素
        Element e = elementLoader.getBeanElement("test1-1");
        boolean result = parser.isLazy(e);
        assertTrue(result);
        //第二个元素
        e = elementLoader.getBeanElement("test1-2");
        result = parser.isLazy(e);
        assertFalse(result);
        //第三个元素
        e = elementLoader.getBeanElement("test1-3");
        result = parser.isLazy(e);
        assertFalse(result);
    }

    @Test
    public void testGetConstructorElements() {
        Element e = elementLoader.getBeanElement("test2");
        List<Element> constructorElements = parser.getConstructorArgsElements(e);
        assertEquals(constructorElements.size(), 2);
    }

    @Test
    public void testGetAttribute() {
        Element e = elementLoader.getBeanElement("test3");
        String value =  parser.getAttribute(e, "class");
        assertEquals(value, "org.silence.Test5");
    }

    @Test
    public void testIsSingleton() {
        Element e = elementLoader.getBeanElement("test4-1");
        boolean result = parser.isSingleton(e);
        assertFalse(result);

        e = elementLoader.getBeanElement("test4-2");
        result = parser.isSingleton(e);
        assertTrue(result);
    }

    @Test
    public void testGetPropertyElements() {
        Element e = elementLoader.getBeanElement("test6");
        List<Element> elements = parser.getPropertyElements(e);
        assertEquals(elements.size(), 2);
    }

    @Test
    public void testGetAutowire() {
        Element e = elementLoader.getBeanElement("test10-1");
        assertEquals(parser.getAttribute(e, "id"), "test10-1");
        Autowire result = parser.getAutowire(e);
        assertEquals(result.getType(), "byName");

        e = elementLoader.getBeanElement("test10-2");
        result = parser.getAutowire(e);
        assertEquals(result.getType(), "no");

        e = elementLoader.getBeanElement("test10-3");
        result = parser.getAutowire(e);
        assertEquals(result.getType(), "no");
    }

    @Test
    public void testGetConstructorValue() {
        Element e = elementLoader.getBeanElement("test11");
        assertEquals(parser.getAttribute(e, "id"), "test11");
        List<LeafElement> result = parser.getConstructorValue(e);
        assertEquals(result.size(), 2);

        ValueElement ve1 = (ValueElement)result.get(0);
        System.out.println(ve1.getValue());
        assertEquals((String)ve1.getValue(),"silence");

        RefElement re = (RefElement)result.get(1);
        assertEquals((String)re.getValue(), "test11");

    }

    @Test
    public void testGetPropertyValue() {
        Element e = elementLoader.getBeanElement("test12");
        List<PropertyElement> eles = parser.getPropertyValue(e);
        assertEquals(eles.size(), 4);
        System.out.println(eles.get(0).getLeafElement().getValue());
        assertEquals(eles.get(0).getName(), "property1");
        assertEquals(eles.get(0).getLeafElement().getValue(), "silence");
        assertEquals(eles.get(0).getLeafElement().getType(), "value");
        System.out.println(eles.get(3).getLeafElement());
        System.out.println(eles.get(3).getLeafElement().getType());
        Object[] obj = (Object[])eles.get(3).getLeafElement().getValue();
        System.out.println(obj[0]);
        System.out.println(obj[1]);

    }

}
