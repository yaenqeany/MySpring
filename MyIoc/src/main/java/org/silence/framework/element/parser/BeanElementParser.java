package org.silence.framework.element.parser;

import org.dom4j.Element;
import org.silence.framework.element.LeafElement;
import org.silence.framework.element.PropertyElement;
import org.silence.framework.element.auto.Autowire;

import java.util.List;

/**
 * 解析装载的element的接口，提供一系列的方法
 * @author Silence.Liu
 */
public interface BeanElementParser {
    /**
     * 判断一个bean元素是否需要延迟加载
     * @param beanElement
     * @return
     */
    public boolean isLazy(Element beanElement);

    /**
     * 获得一个bean元素下的constructor-arg（构造方法参数）的子标签
     * @param bean
     * @return
     */
    public List<Element> getConstructorArgsElements(Element bean);

    /**
     * 得到元素属性为name的属性值
     * @param element
     * @param name
     * @return
     */
    public String getAttribute(Element element, String name);

    /**
     * 判断一个bean元素是否配置为单例
     * @param bean
     * @return
     */
    public boolean isSingleton(Element bean);

    /**
     * 获得一个bean元素下所有property元素
     * @param bean
     * @return
     */
    public List<Element> getPropertyElements(Element bean);

    /**
     * 返回一个bean元素对应的Autowire对象
     * @param bean
     * @return
     */
    public Autowire getAutowire(Element bean);

    /**
     * 获取bean元素下所有constructor-arg的值(包括value和ref)
     * @param bean
     * @return
     */
    public List<LeafElement> getConstructorValue(Element bean);

    /**
     * 获取bean元素下所有property元素的值(包括value和ref)
     * @param bean
     * @return
     */
    List<PropertyElement> getPropertyValue(Element bean);

}
