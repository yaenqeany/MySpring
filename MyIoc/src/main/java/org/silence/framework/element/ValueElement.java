package org.silence.framework.element;

public class ValueElement implements LeafElement {
    /**
     * 同RefNodeElement元素一样也要提供一个成员变量保存元素之间的value值
     */
    private Object value;
    /**
     * 用构造方法将value元素的value值传给成员变量保存
     * @param value
     */
    public ValueElement(Object value) {
        this.value = value;
    }
    /**
     * 重写接口中的getType方法，返回自己的类型，由于该类型是value的所以可以直接返回value字符串
     */
    @Override
    public String getType() {

        return "value";
    }
    /**
     * 重写自接口的getValue方法，返回元素的value值该value已经通过构造方法保存到成员变量
     * 中了，所以直接返回该成员变量就可以了
     */
    @Override
    public Object getValue() {

        return this.value;
    }

}
