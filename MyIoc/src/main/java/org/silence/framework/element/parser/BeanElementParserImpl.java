package org.silence.framework.element.parser;

import org.dom4j.Element;
import org.silence.framework.element.*;
import org.silence.framework.element.auto.Autowire;
import org.silence.framework.element.auto.ByNameAutowire;
import org.silence.framework.element.auto.NoAutowire;
import org.silence.framework.util.IocUtil;

import java.util.ArrayList;
import java.util.List;

public class BeanElementParserImpl implements BeanElementParser {
    /**
     * 判断某一个元素（bean）是否需要延迟加载
     */
    @Override
    public boolean isLazy(Element beanElement) {
        /*
         * 得到该元素的lazy-init属性
         */
        String elementLazy = this.getAttribute(beanElement, "lazy-init");
        /*
         * 得到该元素的上层元素（beans）
         */
        Element parentElement = beanElement.getParent();
        /*
         * 得到该元素的上层元素（beans）的default-lazy-init属性
         */
        Boolean parentElementLazy = new Boolean(this.getAttribute(parentElement, "default-lazy-init"));
        if (parentElementLazy) {
            /*
             * 在根元素需要延迟加载的情况下，子节点（bean）不需要延迟那么不延迟加载
             */
            if ("false".equals(elementLazy)) {
                return false;
            }
            /*
             * 子节点需要延迟加载那么就延迟加载
             */
            return true;
        } else {
            /*
             * 根节点不需要延迟加载的情况下，子节点需要延迟加载那么就延迟加载
             */
            if ("true".equals(elementLazy)) {
                return true;
            }
            /*
             * 根节点不需要延迟加载的情况下，子节点也不需要延迟加载那么就不延迟加载
             */
            return false;
        }

    }
    /**
     * 得到bean元素下的所有的构造方法参数的元素constructor-arg
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Element> getConstructorArgsElements(Element element) {
        /*
         * 得到该元素的所有子元素
         */
        List<Element> children = element.elements();
        /*
         * 定义一个保存所需要的元素的ArrayList集合
         */
        List<Element> result = new ArrayList<Element>();
        /*
         * 遍历所有子元素，若果是constructor-arg元素直接加入到定义的ArrayList
         * 集合中
         */
        for (Element e : children) {
            if ("constructor-arg".equals(e.getName())) {
                result.add(e);
            }
        }
        /*
         * 返回所有的constructor-arg元素的集合
         */
        return result;
    }
    /**
     * 得到元素的name属性值
     */
    @Override
    public String getAttribute(Element element, String name) {
        String value = element.attributeValue(name);
        return value;
    }
    /**
     * 判断元素（bean）是否为单例的
     */
    @Override
    public boolean isSingleton(Element element) {
        /*
         * 如果元素的singleton属性为true（忽略大小写），那么返回true，
         * 如果是其他的字符串或者是空则返回false
         */
        Boolean singleton = new Boolean(this.getAttribute(element, "singleton"));
        return singleton;
    }
    /**
     * 得到某个元素（bean）下的所有property元素
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Element> getPropertyElements(Element element) {
        /*
         * 得到该元素的所有子元素
         */
        List<Element> children = element.elements();
        /*
         * 定义一个保存所需要的元素的ArrayList集合
         */
        List<Element> result = new ArrayList<Element>();

        /*
         * 遍历所有子元素，若果是property元素直接加入到定义的ArrayList
         * 集合中
         */
        for (Element e : children) {
            if("property".equals(e.getName())) {
                result.add(e);
            }
        }
        /*
         * 返回所有的Property元素的集合
         */
        return result;
    }
    /**
     * 得到某个元素（bean）的传入了自动装配类型值的对象
     */
    @Override
    public Autowire getAutowire(Element element) {
        /*
         * 得到某个元素（bean）的自动装配的类型值
         */
        String type = this.getAttribute(element, "autowire");
        /*
         * 得到该元素的父元素（beans）的默认的自动装配类型值
         */
        String parentType = this.getAttribute(element.getParent(), "default-autowire");
        if ("no".equals(parentType)) {
            /*
             * 如果根节点不需要自动装配，子节点需要以name自动装配那么返回一个以name自动装配的
             * ByNameAutowire对象
             */
            if ("byName".equals(type)) {
                return new ByNameAutowire(type);
            }
            /*
             * 如果父节点和子节点都不需要自动装配那么就返回一个表示不需要自动装配的NoAutowire对象
             */
            return new NoAutowire(type);
        } else if ("byName".equals(parentType)) {
            /*
             * 如果根节点需要自动装配而子节点不需要自动装配那么返回一个代表不需要自动装配的NoAutowire
             * 对象
             */
            if ("no".equals(type)) {
                return new NoAutowire(type);
            }
            /*
             * 如果根节点需要自动装配子节点也需要自动装配那么返回一个代表需要以name自动装配的
             *  ByNameAutowire对象
             */
            return new ByNameAutowire(type);
        }
        /*
         * 其他情况返回一个不需要自动装配的对象
         */
        return new NoAutowire(type);
    }
    /**
     * 得到所有的构造方法参数元素中的参数值，也就是ref或value元素
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LeafElement> getConstructorValue(Element element) {
        /*
         * 调用本类中的getConstructorElements方法取得全部的constructor-arg元素
         */
        List<Element> cons = this.getConstructorArgsElements(element);
        /*
         * 定义一个保存所有需要元素的ArrayList
         */
        List<LeafElement> result = new ArrayList<LeafElement>();
        /*
         * 遍历所有的construct-arg元素
         */
        for (Element e : cons) {
            /*
             * 获得constructor-arg下的ref元素或者value元素的其中一个，dtd定义两个元素
             * 只能有其中一个
             */
            List<Element> eles = e.elements();
            /*
             * 调用本类定义的getLeafElement方法获得构造参数元素下的ref或者value元素，封装成
             * RefLeafElement或ValueLeafElement
             */
            LeafElement leafElement = this.getLeafElement(eles.get(0));
            /*
             * 将封装好的RefLeafElement或ValueLeafElement元素加入到ArrayList中
             */
            result.add(leafElement);
        }
        /*
         * 返回NodeList的集合，里面装的是RefLeafElement或ValueLeafElement元素
         */
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PropertyElement> getPropertyValue(Element element) {
        /*
         * 得到某一个元素下的所有property元素
         */
        List<Element> properties = this.getPropertyElements(element);
        /*
         * 定义一个ArrayList的集合准备保存所需要的Property元素
         */
        List<PropertyElement> result = new ArrayList<PropertyElement>();
        /*
         * 遍历所有的Property元素
         */
        for (Element e : properties) {
            /*
             * 获得property下的ref元素或者value元素或者collection中的一个，因为三个元素是互斥的只能存在一个
             */
            List<Element> eles = e.elements();
            /*
             * 得到List中的第一个ref元素或者是value元素
             */
            LeafElement leafElement = getLeafElement(eles.get(0));
            /*
             * 得到property的name属性的值
             */
            String propertyNameAtt = this.getAttribute(e, "name");
            /*
             * 将数据值和property元素的name属性封装成PropertyElement对象
             */
            PropertyElement pe = new PropertyElement(propertyNameAtt, leafElement);
            /*
             * 将该PreopertyElement元素加入到ArrayList中
             */
            result.add(pe);
        }
        /*
         * 返回PropertyElement元素的集合
         */
        return result;
    }
    /**
     * 该方法是根据传过来的Element对象将其封装成RefNodeElement或ValueNodeElement元素
     * 的对象
     * @param leafElement
     * @return
     */
    private LeafElement getLeafElement(Element leafElement) {
        /*
         * 获得传过来的Element元素的名字
         */
        String name = leafElement.getName();
        /*
         * 如果是value元素
         */
        if ("value".equals(name)) {

            /*
             *调用本类定义的方法getValueOfValueElement根据value的type类型返回一个
             *Object数组形式的value值，在构造成为一个ValueElement对象返回
             */
            return new ValueElement(this.getValueOfValueElement(leafElement));
        }
        /*
         * 如果是ref元素
         */
        else if("ref".equals(name)) {
            /*
             * 返回一个将ref元素的bean属性的值传入的RefNodeElement对象
             */
            return new RefElement(this.getAttribute(leafElement, "bean"));
        }
        /*
         * 如果是collection元素
         */
        else if("collection".equals(name)) {
            /*
             * 调用本类的方法getCollectionElement得到一个CollectionElement元素返回
             */
            return this.getCollectionElement(leafElement);
        }
        /*
         * 如果不是这两种元素则返回null
         */
        return null;
    }
    /**
     * 这是一个ValueElement的值的Object数组
     * @param leafElement
     * @return
     */
    private Object getValueOfValueElement(Element leafElement) {
        /*
         * 得到该value元素的type属性的值
         */
        String typeName = leafElement.attributeValue("type");
        /*
         * 得到该value元素的值（即value标签之间的那个值）
         */
        String data = leafElement.getText();
        /*
         * 调用本类的方法返回一个ValueElement的值的数组形式
         */
        return IocUtil.getValue(typeName, data);
    }
    /**
     * 这是根据传过来的一个leafElement元素构造一个CollectionElement元素返回
     * @param leafElement
     * @return
     */
    @SuppressWarnings("unchecked")
    private CollectionElement getCollectionElement(Element leafElement) {
        /*
         * 定义一个保存所需的LeafElement元素的集合
         */
        List<LeafElement> temp = new ArrayList<LeafElement>();
        /*
         * 先得到该Collection元素的type属性值
         */
        String typeName = leafElement.attributeValue("type");
        /*
         * 根据type类型new一个CollectionElement
         */
        CollectionElement ce = new CollectionElement(typeName);
        /*
         * 得到该collection元素的所有子元素
         */
        List<Element> elements = leafElement.elements();
        /*
         * 遍历所有的子元素
         */
        for(Element e:elements) {
            /*
             * 得到Collection下子元素的元素名字
             */
            String tempName = e.getName();
            /*
             * 如果是value元素则调用对应方法得到该元素的值，并根据该值new一个ValueElement并保存到temp集合中
             */
            if("value".equals(tempName)) {
                temp.add(new ValueElement(this.getValueOfValueElement(e)));
            }
            /*
             * 如果是ref元素则调用对应的方法得到该元素的对应的值，并创建一个RefElement元素加到temp中
             */
            else if("ref".equals(tempName)) {
                temp.add(new RefElement(this.getAttribute(e, "bean")));
            }
        }
        /*
         * 将所得到的ValueElement或Refelement元素加到CollectionElement的集合中去
         */
        ce.setList(temp);
        /*
         * 返回构造好的CollectionElement
         */
        return ce;
    }

}
