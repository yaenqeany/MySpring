import org.dom4j.Document;
import org.dom4j.Element;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.silence.framework.holder.XmlDocumentHolder;

public class XmlDocumentHolderTest {
    private XmlDocumentHolder xmlHolder;

    @Before
    public void setUp() throws Exception {
        xmlHolder = new XmlDocumentHolder();
    }

    @After
    public void tearDown() throws Exception {
        xmlHolder = null;
    }

    //测试正常情况
    @Test
    public void testGetDocument1() {
        String filePath = "src/main/resources/test.xml";//相对路径是指相对当前module的路径
        //获得Document对象
        Document doc1 = xmlHolder.getDocument(filePath);
        //看是否为空，为空测试失败
        Assert.assertNotNull(doc1);
        //得到xml文档根元素
        Element root = doc1.getRootElement();
        //判断根元素是否为beans，不是beans测试失败
        Assert.assertEquals(root.getName(), "beans");
        //再获取一次Document对象，看是否一致
        Document doc2 = xmlHolder.getDocument(filePath);
        System.out.println(doc1);
        System.out.println(doc1);
        Assert.assertEquals(doc1, doc2);
    }
}
